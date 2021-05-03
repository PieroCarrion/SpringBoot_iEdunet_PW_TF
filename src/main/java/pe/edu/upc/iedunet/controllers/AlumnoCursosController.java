package pe.edu.upc.iedunet.controllers;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upc.iedunet.helpers.UserTesting;
import pe.edu.upc.iedunet.models.entities.Alumno;
import pe.edu.upc.iedunet.models.entities.AlumnoClase;
import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.models.entities.Comunicado;
import pe.edu.upc.iedunet.models.entities.Curso;
import pe.edu.upc.iedunet.services.AlumnoService;
import pe.edu.upc.iedunet.services.ClaseService;
import pe.edu.upc.iedunet.viewmodels.CursoViewModel;
import pe.edu.upc.iedunet.viewmodels.EmailViewModel;

@Controller
@RequestMapping("/alumno/cursos")
public class AlumnoCursosController {

	@Autowired
	private AlumnoService alumnoService;
	//
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private ClaseService claseService;

	//
	@GetMapping()
	public String viewCursos(Model model) {
		System.err.println("Hola");
		try {
			List<CursoViewModel> cursos = alumnoService.findCursos(UserTesting.Users.getIdAlumno());
			model.addAttribute("cursos", cursos);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "alumno/cursos/index";
	}
	@GetMapping("info/index")
	public String viewCurso(@RequestParam(name = "id", required = false) int id, Model model) {

		try {
			System.out.println("ID de la clase: " + id);
			CursoViewModel curso = alumnoService.findCurso(UserTesting.Users.getIdAlumno(), id);
			int idClase = id;
			String nombreCurso = "none";
			String grado = "none";
			Boolean hasComunicados = false;
			
			if (curso != null) {
				model.addAttribute("curso", curso);
				nombreCurso = curso.getNombreCurso();
				grado = curso.getGrado();
			} else {
				return "/alumno/cursos/";
			}
			
			List<Comunicado> list = claseService.findComunicadoByClase(id);
			if(list.size()>0) {
				hasComunicados = true;
				Comunicado comunicado1 = list.get(0); list.remove(0);
				model.addAttribute("comunicado1", comunicado1);
			}
			
			model.addAttribute("nombreCurso",nombreCurso);
			model.addAttribute("grado",grado);
			model.addAttribute("idClase",idClase);
			model.addAttribute("linkClase",curso.getLinkClase());
			model.addAttribute("hasComunicados", hasComunicados);
			model.addAttribute("comunicados", list);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "alumno/cursos/info/index";
	}
	@GetMapping("info/email")
	public String viewEmailForm(@RequestParam(name = "id", required = false) int id, Model model)
			throws AddressException, MessagingException, IOException {
		try {
			EmailViewModel emailViewModel = new EmailViewModel();
			emailViewModel.setIdCurso(id);
			emailViewModel.setIdAlumno(UserTesting.Users.getIdAlumno());
			Alumno alumno = alumnoService.findById(UserTesting.Users.getIdAlumno()).get();
			emailViewModel.setNombresAlumno(alumno.getUsuario().getNombre() + " "+ alumno.getUsuario().getApellido());
			CursoViewModel tempModel = alumnoService.findCurso(UserTesting.Users.getIdAlumno(),id);
			emailViewModel.setMailTo(tempModel.getEmailProfesor());
			emailViewModel.setNombreCurso(tempModel.getNombreCurso());
			emailViewModel.setNombreProfesor(tempModel.getDocenteCurso());
//			System.out.println(emailViewModel.getNombreCurso());
//			System.out.println(emailViewModel.getIdAlumno());
//			System.out.println(emailViewModel.getIdCurso());
//			System.out.println(emailViewModel.getNombreProfesor());
			model.addAttribute("emailViewModel", emailViewModel);
			model.addAttribute("idClaseCurso",id);
			model.addAttribute("nombreCurso",tempModel.getNombreCurso());
			model.addAttribute("nombreProfesor",emailViewModel.getNombreProfesor());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "alumno/cursos/info/email";
	}

	@PostMapping("info/sendEmail")
	public String sendEmail(@ModelAttribute("emailViewModel") EmailViewModel emailInfo,  SessionStatus status) {
		try {
			String mailMsg = "Codigo Alumno: " + emailInfo.getIdAlumno() +
							  "\n Nombres Alumno: " + emailInfo.getNombresAlumno() +
							  "\n Nombre Curso: " + emailInfo.getNombreCurso() +
							  "\n Nombre Destinatario: " + emailInfo.getNombreProfesor() +
							  "\n Correo Destinatario: " + emailInfo.getMailTo() +
							  "\n Mensaje: " + emailInfo.getMessage();
			SimpleMailMessage msg = new SimpleMailMessage();
			System.out.print(mailMsg);
			msg.setFrom("iEduNet Support <correopruebas2020progra@gmail.com>");
			msg.setTo(emailInfo.getMailTo());
			msg.setSubject(emailInfo.getSubject());
			msg.setText(mailMsg);
			javaMailSender.send(msg);
			status.setComplete();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		String redirect = "redirect:index?id="+ emailInfo.getIdCurso();
		return redirect;
	}
}
