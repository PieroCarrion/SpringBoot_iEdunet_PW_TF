package pe.edu.upc.iedunet.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pe.edu.upc.iedunet.models.entities.Authority;
import pe.edu.upc.iedunet.models.entities.Usuario;
import pe.edu.upc.iedunet.models.repositories.AuthorityRepository;
import pe.edu.upc.iedunet.models.repositories.UsuarioRepository;

@Service
public class InitDB implements CommandLineRunner{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	/*@Autowired
	private PasswordEncoder passwordEncoder;*/
	
	@Override
	public void run(String... args) throws Exception {
		/*Usuario user1 = usuarioRepository.findById(new Long(3)).get();//ACA COLOQUEN EL ID DEL USUARIO QUE TIENEN REGISTRADO, MODIFIQUEN UNO PARA ALUMNO Y OTRO PARA PROFE
		user1.setPassword(new BCryptPasswordEncoder().encode("123"));
		user1.setUsername("profesor2");
		
		Usuario user2 = usuarioRepository.findById(new Long(1)).get();//ACA COLOQUEN EL ID DEL USUARIO QUE TIENEN REGISTRADO, MODIFIQUEN UNO PARA ALUMNO Y OTRO PARA PROFE
		user2.setPassword(new BCryptPasswordEncoder().encode("123"));
		user2.setUsername("alumno");
		
		Usuario user3 = usuarioRepository.findById(new Long(2)).get();//ACA COLOQUEN EL ID DEL USUARIO QUE TIENEN REGISTRADO, MODIFIQUEN UNO PARA ALUMNO Y OTRO PARA PROFE
		user3.setPassword(new BCryptPasswordEncoder().encode("123"));
		user3.setUsername("alumno2");
		
		Usuario user4 = usuarioRepository.findById(new Long(28)).get();//ACA COLOQUEN EL ID DEL USUARIO QUE TIENEN REGISTRADO, MODIFIQUEN UNO PARA ALUMNO Y OTRO PARA PROFE
		user4.setPassword(new BCryptPasswordEncoder().encode("123"));
		user4.setUsername("profesor");
		//Luego de compilar, el usuario ya estaria actualizado. Luego de ello, tienen que actualizar la tabla Authorities en el PgAdmin para relacionar el usuario con sul Rol de Alumno o Profesor
		
		this.usuarioRepository.saveAndFlush(user1);
		this.usuarioRepository.saveAndFlush(user2);
		this.usuarioRepository.saveAndFlush(user3);
		this.usuarioRepository.saveAndFlush(user4);*/
		
		//Como recomendacion, el Username que sea alumno o profesor mas un numero que diferencie (alumno, alumno2, alumno3, etc), el password que sea 123
	}

}
