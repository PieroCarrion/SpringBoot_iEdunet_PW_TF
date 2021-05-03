package pe.edu.upc.iedunet.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.sql.ordering.antlr.GeneratedOrderByFragmentRendererTokenTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import pe.edu.upc.iedunet.models.entities.Usuario;

public class UsuarioDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	// Inyección de dependencia
	private Usuario usuario;
	public UsuarioDetails(Usuario usuario) {
		super();
		this.usuario = usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		this.usuario.getAuthorities().forEach(authority -> {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthority());
			grantedAuthorities.add(grantedAuthority);
		});
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return this.usuario.getPassword();
	}

	@Override
	public String getUsername() {
		return this.usuario.getUsername();
	}
	
	public long getIdAlumno() {
		return this.usuario.getAlumno().getId();
	}
	
	public long getIdProfesor() {
		return this.usuario.getProfesor().getId();
	}
	
	public char getType() {
		if(this.usuario.getCodigoUsuario().contains("T")) {
			return 'T';
		}
		else if(this.usuario.getCodigoUsuario().contains("S")){
			return 'S';
		}
		else {
			return '0';
		}
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.usuario.isEnable();
	}

}
