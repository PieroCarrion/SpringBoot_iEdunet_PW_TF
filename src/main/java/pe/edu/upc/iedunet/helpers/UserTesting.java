package pe.edu.upc.iedunet.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import pe.edu.upc.iedunet.models.repositories.UsuarioRepository;
import pe.edu.upc.iedunet.security.UsuarioDetails;

public class UserTesting {//ESTA CLASE ES PARA SIMULAR AL ALUMNO O PROFESOR EN SESION
	
	public static class Users{
		private static int idAlumno = 1;
		private static int idProfesor= 2;
		//private static Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//private static UsuarioDetails usuarioDetails = (UsuarioDetails)authentication.getPrincipal();
		
		
		public static int getIdAlumno() {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UsuarioDetails usuarioDetails = (UsuarioDetails)authentication.getPrincipal();
			System.err.println("ID del usuario en sesion Alumno: " + usuarioDetails.getIdAlumno());
			return (int) usuarioDetails.getIdAlumno();
		}
		
		public void setIdAlumno(int idAlumno) {
			Users.idAlumno = idAlumno;
		}

		public static int getIdProfesor() {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UsuarioDetails usuarioDetails = (UsuarioDetails)authentication.getPrincipal();
			System.err.println("ID del usuario en sesion Profesor: " + usuarioDetails.getIdProfesor());
			return (int) usuarioDetails.getIdProfesor();
		}

		public static void setIdProfesor(int idProfesor) {
			Users.idProfesor = idProfesor;
		}
		
		public static Boolean isAlumno() {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UsuarioDetails usuarioDetails = (UsuarioDetails)authentication.getPrincipal();
			if(usuarioDetails.getType() == 'S') {
				return true;
			}
			else{
				return false;
			}

		}
		
	}
}
