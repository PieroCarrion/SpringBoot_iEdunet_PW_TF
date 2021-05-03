package pe.edu.upc.iedunet.models.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "usuarios")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "username", length = 16, nullable = true)
	private String username;
	
	@Column(name = "password", length = 255, nullable = true)
	private String password;
	
	@Column(name = "enable", nullable = true)
	private boolean enable;
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Authority> authorities;
	
	public Usuario() {
		this.enable=true;
		this.authorities = new ArrayList<>();
	}
	
	public Usuario(String username, String password) {
		this.username = username;
		this.password = password;
		this.enable=true;
		this.authorities = new ArrayList<>();
	}
	
	@ManyToOne
	@JoinColumn(name = "colegio_id")
	private Colegio colegio;
	
	@Column(name = "codigo_usuario", length = 16, nullable = false)
	private String codigoUsuario;
	
	@Column(name = "dni", length = 10, nullable = false)
	private String dni;
	
	@Column(name = "nombre", length = 16, nullable = false)
	private String nombre;
	
	@Column(name = "apellido", length = 16, nullable = false)
	private String apellido;
	
	@Column(name = "email", length = 50, nullable = false)
	private String email;
	
	@Column(name = "fecha_nacimiento", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	
	@Column(name = "fecha_registro", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date fechaRegistro;
	
	@Column(name = "telefono", length = 15, nullable = true)
	private String telefono;
	
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Alumno alumno;
    
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Profesor profesor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Colegio getColegio() {
		return colegio;
	}

	public void setColegio(Colegio colegio) {
		this.colegio = colegio;
	}

	public String getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(String codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}
	

}
