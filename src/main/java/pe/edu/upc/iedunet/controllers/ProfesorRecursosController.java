package pe.edu.upc.iedunet.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upc.iedunet.helpers.UserTesting;
import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.models.entities.Recurso;
import pe.edu.upc.iedunet.services.ClaseService;
import pe.edu.upc.iedunet.services.ProfesorService;
import pe.edu.upc.iedunet.services.RecursosService;
import pe.edu.upc.iedunet.viewmodels.CursoViewModel;
import pe.edu.upc.iedunet.viewmodels.RecursoViewModel;

@Controller
@RequestMapping("/profesor/cursos/recursos")
public class ProfesorRecursosController {
	@Autowired
	private ProfesorService profesorService;
	@Autowired
	private RecursosService recursoService;
	@Autowired
	private ClaseService claseService;
	@GetMapping()
	public String viewRecursos(@RequestParam(name = "id", required = false) int id, Model model) {
		try {
			CursoViewModel curso = profesorService.findCurso(UserTesting.Users.getIdProfesor(), id);
			int idClase = id;
			String nombreCurso = "none";
			String grado = "none";

			if (curso != null) {
				model.addAttribute("curso", curso);
				nombreCurso = curso.getNombreCurso();
				System.err.println(nombreCurso);
				grado = curso.getGrado();
			} else {
				return "profesor/cursos/";
			}
			model.addAttribute("nombreCurso", nombreCurso);
			model.addAttribute("grado", grado);
			model.addAttribute("idClase", idClase);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "profesor/cursos/recursos/index";
	}

	@GetMapping("/viewRecurso")
	public String viewRecursosDisponibles(@RequestParam(name = "id", required = false) int id, Model model) {
		try {
			CursoViewModel curso = profesorService.findCurso(UserTesting.Users.getIdProfesor(), id);
			List<Recurso> recursos = recursoService.findAllByClase(id);
			int idClase = id;
			String nombreCurso = "none";
			String grado = "none";

			if (curso != null) {
				model.addAttribute("curso", curso);
				nombreCurso = curso.getNombreCurso();
				System.err.println(nombreCurso);
				grado = curso.getGrado();
			} else {
				return "profesor/cursos/recursos";
			}
			model.addAttribute("nombreCurso", nombreCurso);
			model.addAttribute("grado", grado);
			model.addAttribute("idClase", idClase);
			model.addAttribute("recursos", recursos);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "profesor/cursos/recursos/viewRecurso";
	}
	@GetMapping("/subirRecurso")
	public String viewSubirRecursos(@RequestParam(name = "id", required = false) int id, Model model) {
		try {
			CursoViewModel curso = profesorService.findCurso(UserTesting.Users.getIdProfesor(), id);
			int idClase = id;
			String nombreCurso = "none";
			String grado = "none";
			
			if (curso != null) {
				model.addAttribute("curso", curso);
				nombreCurso = curso.getNombreCurso();
				System.err.println(nombreCurso);
				grado = curso.getGrado();
			} else {
				return "profesor/cursos/";
			}
			RecursoViewModel recurso = new RecursoViewModel();
			recurso.setIdClase(idClase);
			model.addAttribute("nombreCurso", nombreCurso);
			model.addAttribute("grado", grado);
			model.addAttribute("idClase", idClase);
			model.addAttribute("recurso", recurso);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "profesor/cursos/recursos/subirRecurso";
	}
	@PostMapping("saveRecurso")
	public String subirRecursos(@ModelAttribute("recurso") RecursoViewModel recurso, SessionStatus status) {
		try {
			Recurso recursoT = new Recurso();
			recursoT.setId(0);
			recursoT.setDescripcion(recurso.getDescripcion());
			recursoT.setLinkDocumento(recurso.getLinkDocumento());
			recursoT.setNombre(recurso.getNombre());
			recursoT.setClase(claseService.findById(recurso.getIdClase()).get());
			Date fecha = new Date();
			recursoT.setFechaPublicacion(fecha.toString());
			recursoService.save(recursoT);
			status.setComplete();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		String redirect = "redirect:viewRecurso?id=" + recurso.getIdClase();
		return redirect;
	}
}
