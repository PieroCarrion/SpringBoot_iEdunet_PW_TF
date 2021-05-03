package pe.edu.upc.iedunet.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upc.iedunet.models.entities.ActividadAcademica;
import pe.edu.upc.iedunet.models.entities.Alumno;
import pe.edu.upc.iedunet.models.entities.Calificacion;
import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.models.repositories.ActividadAcademicaRepository;
import pe.edu.upc.iedunet.services.ActividadAcademicaService;
import pe.edu.upc.iedunet.services.ClaseService;
import pe.edu.upc.iedunet.viewmodels.CalificacionViewModel;
import pe.edu.upc.iedunet.viewmodels.ExamenViewModel;

@Controller
@RequestMapping("/profesor/cursos/examenes")
public class ProfesorExamenesController {
	@Autowired
	private ActividadAcademicaService actividadAcademicaService;
	@Autowired
	private ClaseService claseService;

	@GetMapping()
	public String viewExams(@RequestParam(name = "id", required = false) int id, Model model) {
		ExamenViewModel newExam = new ExamenViewModel();
		newExam.setIdClase(id);
		System.err.println("examenes de la clase: " + id);
		try {

			List<ExamenViewModel> list = actividadAcademicaService.findAllExams(id);

			Boolean hasExams = false;
			int idClase = id;
			String nombreCurso = "none";
			String grado = "none";

			if (list.size() > 0) {
				hasExams = true;
				nombreCurso = list.get(0).getCurso();
				grado = list.get(0).getGrado();
			}

			model.addAttribute("nombreCurso", nombreCurso);
			model.addAttribute("grado", grado);
			model.addAttribute("hasExams", hasExams);
			model.addAttribute("idClase", idClase);
			model.addAttribute("exams", list);

			model.addAttribute("exam", newExam);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "profesor/cursos/examenes/index";
	}

	@PostMapping("save-exam")
	public String save(@ModelAttribute("exam") ExamenViewModel examen, SessionStatus status) {
		try {
//Clase en cuestion
			Clase clase = new Clase();
			clase.setId(examen.getIdClase());

			System.err.println("En la DB se guardara: " + examen.getDescripcion() + " de la clase "
					+ examen.getIdClase() + " con link " + examen.getLink() + "e ID de AA: " + examen.getId());

			ActividadAcademica exam = new ActividadAcademica();
			exam.setClase(clase);
			exam.setId(0);

			exam.setDescripcion(examen.getFullDescripcion());
			Date current = new Date();
			exam.setFechaPublicacion(current);
			exam.setFechaMaxEntrega(examen.getFechaExamen());

			actividadAcademicaService.save(exam);

			status.setComplete();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}

		String redirect = "redirect:?id=" + examen.getIdClase();
		return redirect;
	}

	@GetMapping("viewentregas")
	public String viewCalificaciones(@RequestParam(name = "id", required = false) int id, Model model) {
		CalificacionViewModel newCalificacion = new CalificacionViewModel();
		newCalificacion.setIdExamen(id);

		try {

			List<CalificacionViewModel> list = claseService.findAlumnosWithExams(id);
			Clase clase = claseService.findById(list.get(0).getIdClase()).get();
			Boolean hasExams = false;
			int idClase = id;
			String nombreCurso = "none";
			String grado = "none";

			if (list.size() > 0) {
				hasExams = true;
				nombreCurso = clase.getCurso().getNombre();
				grado = clase.getGrado();
			}
			model.addAttribute("nombreCurso", nombreCurso);
			model.addAttribute("grado", grado);
			model.addAttribute("hasExams", hasExams);
			model.addAttribute("idClase", idClase);
			model.addAttribute("notas", list);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "profesor/cursos/examenes/viewentregas";
	}

	@GetMapping("scoreExamen")
	public String viewAlumnoCalificacion(@RequestParam(name = "idA", required = false) int idA,@RequestParam(name = "idC", required = false) int idC, Model model) {
		try {
			CalificacionViewModel newCalificacion = new CalificacionViewModel();
		
			newCalificacion.setIdAlumno(idA);
			newCalificacion.setIdExamen(idC);
		
			System.err.println("prueba en el get"+newCalificacion.getIdCalificacion());
			model.addAttribute("calificacion", newCalificacion);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "profesor/cursos/examenes/scoreExamen";
	}

	@PostMapping("save-score")
	public String saveCalificacion(@ModelAttribute("notas") CalificacionViewModel calificacion, SessionStatus status) {
		try {

			System.out.println(calificacion.getIdExamen()+ " "+calificacion.getIdAlumno()+ "ver esto importante "+calificacion.getIdCalificacion());
			Date current = new Date();
			calificacion.setFechaRevision(current);

			actividadAcademicaService.saveCalificacion(calificacion);

			status.setComplete();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}

		String redirect = "redirect:viewentregas?id=" + calificacion.getIdExamen();
		return redirect;

	}

}
