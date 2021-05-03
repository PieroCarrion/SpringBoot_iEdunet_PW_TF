package pe.edu.upc.iedunet.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import pe.edu.upc.iedunet.models.entities.AlumnoClase;
import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.models.entities.Comunicado;
import pe.edu.upc.iedunet.services.AlumnoService;
import pe.edu.upc.iedunet.services.ActividadAcademicaService;
import pe.edu.upc.iedunet.services.ClaseService;
import pe.edu.upc.iedunet.services.CursoService;
import pe.edu.upc.iedunet.services.ProfesorService;
import pe.edu.upc.iedunet.viewmodels.CursoViewModel;
import pe.edu.upc.iedunet.viewmodels.EmailViewModel;

@Controller
@RequestMapping("/profesor/cursos")
public class ProfesorCursosController {

	@Autowired
	private CursoService cursoService;

	@Autowired
	private ClaseService claseService;
	@Autowired
	private ProfesorService profesorService;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private AlumnoService alumnoService;

	@Autowired
	private ActividadAcademicaService actividadAcademicaService;


	@GetMapping()
	public String viewCursos(Model model) {
		try {

			/*
			 * List<CursoViewModel> clases = new ArrayList<CursoViewModel>(); CursoViewModel
			 * test = new CursoViewModel(); test.setId(1); test.setIdClase(1);
			 * test.setNombreCurso("Curso Test"); clases.add(test);
			 */

			List<Clase> clasesProfe = profesorService.findById(UserTesting.Users.getIdProfesor()).get().getClases();

			for (Clase clase : clasesProfe) {
				System.err.println(clase.getId() + "-" + clase.getCurso().getNombre() + "-" + clase.getGrado());
			}

			// Que se dicta en esta clase
			model.addAttribute("clases", clasesProfe);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "profesor/cursos/index";
	}

/////////////////////
/////////////////////
//SECCION INFO CURSOS////
/////////////////////
/////////////////////

//US2- Visualizar informacion del curso - Profesor

	@GetMapping("info/index")
	public String viewCursoInfo(@RequestParam(name = "id", required = false) int id, Model model) {

		try {
			System.out.println("ID de la clase: " + id);

			CursoViewModel curso = profesorService.findCurso(UserTesting.Users.getIdProfesor(), id);
			/*
			 * curso.setHorarioCurso("Lunes 10:00 - 12:00 AM"); curso.setIdClase(1);
			 * curso.setNombreCurso("Curso de Prueba");
			 * curso.setGrado("5to Grado de Primaria");
			 */

			int idClase = id;
			String nombreCurso = "none";
			String grado = "none";
			Boolean hasComunicados = false;
			
			//actividadAcademicaService.findAAbyClase(id);
			
			if (curso != null) {
				model.addAttribute("curso", curso);
				nombreCurso = curso.getNombreCurso();
				System.err.println(nombreCurso);
				grado = curso.getGrado();
			} else {
				return "profesor/cursos/";
			}
			List<Comunicado> list = claseService.findComunicadoByClase(id);
			if (list.size() > 0) {
				hasComunicados = true;
				Comunicado comunicado1 = list.get(0);
				list.remove(0);
				model.addAttribute("comunicado1", comunicado1);
			}

			model.addAttribute("nombreCurso", nombreCurso);
			model.addAttribute("grado", grado);
			model.addAttribute("idClase", idClase);
			model.addAttribute("hasComunicados", hasComunicados);
			model.addAttribute("comunicados", list);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "profesor/cursos/info/index";
	}

	@PostMapping("save-link")
	public String saveLinkClase(@ModelAttribute("curso") CursoViewModel clase, SessionStatus status) {

		try {

			Optional<Clase> optional = claseService.findById(clase.getId());
			if (optional.isPresent()) {
				Clase clasedb = optional.get();

				clasedb.setLinktoClass(clase.getLinkClase());
				claseService.update(clasedb);
				status.setComplete();
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}

		String redirect = "redirect:info/index?id=" + clase.getId();
		return redirect;
	}

/////////////////////
/////////////////////
//SECCION COMUNICADOS////
/////////////////////
/////////////////////

	@GetMapping("info/addcomunicado")
	public String addComunicado(@RequestParam("id") Integer id, Model model) {

		try {
			System.out.println("ID de la clase: " + id);
			CursoViewModel curso = profesorService.findCurso(UserTesting.Users.getIdProfesor(), id);
			Comunicado comunicado = new Comunicado();
			Clase claseOwner = new Clase();
			claseOwner.setId(id);
			comunicado.setClase(claseOwner);

			int idClase = id;
			String nombreCurso = "none";
			String grado = "none";

			if (curso != null) {
				model.addAttribute("curso", curso);
				nombreCurso = curso.getNombreCurso();
				grado = curso.getGrado();
			} else {
				return "profesor/cursos/";
			}

			model.addAttribute("nombreCurso", nombreCurso);
			model.addAttribute("grado", grado);
			model.addAttribute("idClase", idClase);
			model.addAttribute("comunicado", comunicado);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "profesor/cursos/info/addcomunicado";
	}

	@PostMapping("save-comunicado")
	public String saveComunicado(@ModelAttribute("comunicado") Comunicado comunicado, SessionStatus status) {

		try {
			Date fechaActual = new Date();
			comunicado.setFecha(fechaActual);
			System.err.println("Se grabara el comunicado " + comunicado.getDescripcion() + " para la clase "
					+ comunicado.getClase().getId());

			profesorService.saveComunicado(comunicado);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}

		String redirect = "redirect:info/index?id=" + comunicado.getClase().getId();
		return redirect;
	}

/////////////////////
/////////////////////
//SECCION CORREO////
/////////////////////
/////////////////////
	@GetMapping("info/email")
	public String viewEmailForm(@RequestParam(name = "id", required = false) int id, Model model) {
		try {
			EmailViewModel emailViewModel = new EmailViewModel(); 
			List<EmailViewModel> listaAlumnos = new ArrayList<>();
			//alumnoClaseService.findById()
			//Alumno alumno = alumnoService.findById(UserTesting.Users.getIdAlumno()).get();
			//emailViewModel.setNombresAlumno(alumno.getUsuario().getNombre() + " "+ alumno.getUsuario().getApellido());
			CursoViewModel tempModel =  profesorService.findCurso(UserTesting.Users.getIdProfesor(),id);
			emailViewModel.setIdClase(id);
			emailViewModel.setIdProfesor(UserTesting.Users.getIdProfesor());
			emailViewModel.setMailTo(tempModel.getEmailProfesor());
			emailViewModel.setNombreCurso(tempModel.getNombreCurso());
			emailViewModel.setNombreProfesor(tempModel.getDocenteCurso());
			model.addAttribute("emailViewModel", emailViewModel);
			model.addAttribute("idClaseCurso", id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		return "/profesor/cursos/info/email";
	}

	@PostMapping("info/sendEmail")
	public String sendEmail(@ModelAttribute("emailViewModel") EmailViewModel emailInfo, SessionStatus status) {
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			for(AlumnoClase aC : claseService.findById(emailInfo.getIdClase()).get().getAlumnosClases()) {
				msg = new SimpleMailMessage();
				String mailMsg = 
				"Codigo Profesor: " +	aC.getClase().getProfesor().getId() + 
				"\n Nombres Alumno: "+ aC.getAlumno().getUsuario().getEmail() + 
				"\n Nombre Curso: " + emailInfo.getNombreCurso() + 
				"\n Nombre Destinatario: " + aC.getAlumno().getUsuario().getNombre() + 
				"\n Correo Destinatario: "+	aC.getAlumno().getUsuario().getEmail() +
				"\n Asunto: " + emailInfo.getSubject() +
				"\n Mensaje: " + emailInfo.getMessage();
				System.out.println(mailMsg);
				msg.setFrom("iEduNet Support <correopruebas2020progra@gmail.com>");
				msg.setTo(aC.getAlumno().getUsuario().getEmail());
				msg.setSubject(emailInfo.getSubject());
				msg.setText(mailMsg);
				javaMailSender.send(msg);
			}
			status.setComplete();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e.getMessage());
		}
		String redirect = "redirect:info/index?id="+ emailInfo.getIdClase();
		return redirect;
	}
}
