package pe.edu.upc.iedunet.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pe.edu.upc.iedunet.helpers.HorarioGenerator;
import pe.edu.upc.iedunet.helpers.UserTesting;
import pe.edu.upc.iedunet.models.entities.ActividadAcademica;
import pe.edu.upc.iedunet.models.entities.Alumno;
import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.models.entities.Curso;
import pe.edu.upc.iedunet.services.ActividadAcademicaService;
import pe.edu.upc.iedunet.services.AlumnoService;
import pe.edu.upc.iedunet.services.ClaseService;
import pe.edu.upc.iedunet.services.CursoService;
import pe.edu.upc.iedunet.viewmodels.CursoViewModel;
//import pe.edu.upc.iedunet.viewmodels.CursoViewModel;
import pe.edu.upc.iedunet.viewmodels.ExamenViewModel;
import pe.edu.upc.iedunet.viewmodels.HorarioItemViewModel;
import pe.edu.upc.iedunet.viewmodels.HorarioViewModel;

@Controller
@RequestMapping("/alumno")
public class AlumnoController {
	@Autowired
	private AlumnoService alumnoService;
	// Para obtener data de la BD y enviarlo al Front
	//@RequestMapping(value = "/alumno/", method = RequestMethod.GET)
	@GetMapping
	public String inicio(Model model) {
		try {
			List<Alumno> alumnos = alumnoService.findAll();
			
			model.addAttribute("alumnos", alumnos);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		// Devuelve el nombre del archivo HTML
		return "alumno/index";
		//return "redirect:alumno/cursos";
	}

	//@RequestMapping(value = "/alumno/horario", method = RequestMethod.GET)
	@GetMapping("horario")
	public String horario(Model model) {
		HorarioGenerator horarioGenerator = new HorarioGenerator();

		try {
			// ACA SE OBTENDRIA EL ALUMNO EN SESION PARA MOSTRAR SU HORARIO
			List<HorarioViewModel> horarios = alumnoService.findHorario(UserTesting.Users.getIdAlumno());

			// Se pasa la lista de alumnos al Generator para formatear para mostrar en el
			// Table
			List<HorarioItemViewModel> horariositems = horarioGenerator.GenerateHorario(horarios);

			model.addAttribute("horariositems", horariositems);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		} // Devuelve el nombre del archivo HTML
		return "alumno/horario";
	}

	@GetMapping("calificaciones")
	public String calificaciones(Model model) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "alumno/calificaciones";
	}
	
	@GetMapping("datoUsuario")
	public String getUsuario(Model model) {
		try {
			model.addAttribute("datoUsuario",alumnoService.findById(UserTesting.Users.getIdAlumno()));
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "alumno/datoUsuario";
	}
}