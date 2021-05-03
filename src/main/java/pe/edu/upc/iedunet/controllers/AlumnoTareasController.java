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
import pe.edu.upc.iedunet.helpers.UserTesting;
import pe.edu.upc.iedunet.models.entities.ActividadAcademica;
import pe.edu.upc.iedunet.models.entities.Alumno;
import pe.edu.upc.iedunet.models.entities.Calificacion;
import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.services.ActividadAcademicaService;
import pe.edu.upc.iedunet.services.AlumnoService;
import pe.edu.upc.iedunet.services.ClaseService;
import pe.edu.upc.iedunet.services.CursoService;
import pe.edu.upc.iedunet.viewmodels.ExamenViewModel;
import pe.edu.upc.iedunet.viewmodels.TareaViewModel;

@Controller
@RequestMapping("/alumno/cursos/tareas")
public class AlumnoTareasController {
	@Autowired
	private ActividadAcademicaService actividadAcademicaService;
	
	@Autowired
	private ClaseService claseService;
	

	@GetMapping()
	public String viewTareas(@RequestParam(name = "id", required = false) int id, Model model) {

		try {
			List<TareaViewModel> list = actividadAcademicaService.findHomeworksByClase(id);

			Boolean hasExams = false;
			int idClase = id;
			String nombreCurso = "none";
			String grado = "none";

			if (list.size() > 0) {
				hasExams = true;
				nombreCurso = list.get(0).getCurso();
				grado = list.get(0).getGrado();
			}
			else {
				Clase clase = claseService.findById(id).get();
				nombreCurso = clase.getCurso().getNombre();
				grado = clase.getGrado();
			}
			
			System.err.println("tiene examenes? " + hasExams);
			System.err.println("curso " + nombreCurso);

			model.addAttribute("nombreCurso", nombreCurso);
			model.addAttribute("grado", grado);
			model.addAttribute("hasTareas", hasExams);
			model.addAttribute("idClase", idClase);
			model.addAttribute("tareas", list);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "alumno/cursos/tareas/index";
	}

	@GetMapping("tarea")
	public String ViewTarea(@RequestParam(name = "id", required = false) int id, Model model) {
		TareaViewModel newTareaLink = new TareaViewModel();
		newTareaLink.setIdActividad(id);
		System.err.println("tareas de la clase en ViewTarea: " + id);
		try {
			Boolean hasTareas = false;
			int idTarea = id;
			String fecha = "none";
			String linkDoc="www.asdsddjhdjkdkasd.com";
			
			model.addAttribute("idActividadAcademica", id);
			model.addAttribute("fecha", fecha);
			model.addAttribute("hasTareas", hasTareas);
			model.addAttribute("idTarea", idTarea);
			model.addAttribute("linkDoc", linkDoc);
			model.addAttribute("tarea", newTareaLink);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "alumno/cursos/tareas/tarea";
	}

	@PostMapping("save-tarea")
	public String SubirTareaAlumno(@ModelAttribute("tarea") TareaViewModel tarea, SessionStatus status) {

		try {
			Calificacion calificaciones = new Calificacion();
			calificaciones.setLinkDoc(tarea.getLinkDoc());
			
			ActividadAcademica actividadAcademica = actividadAcademicaService.findById(tarea.getIdActividad()).get();

			System.out.println("En la DB se guardara: " + tarea.getDescripcion() + " de la actividad academica " + tarea.getIdActividad()
					+ " con link " + tarea.getLinkDoc() + " de la clase " + actividadAcademica.getClase().getId());
			
			
			
			Calificacion tareas = new Calificacion();
			tareas.setActividadAcademica(actividadAcademica);
			
			Alumno alumno = new Alumno();
			alumno.setId(UserTesting.Users.getIdAlumno());
			
			tareas.setAlumno(alumno);
			
			Date current = new Date();
			tareas.setFechaEntrega(current);
			//tareas.setFechaRevision(tareas.getFechaRevision());

			tareas.setLinkDoc(tarea.getLinkDoc());
			
			actividadAcademicaService.save(tareas);

			status.setComplete();
			
			String redirect = "redirect:?id=" + actividadAcademica.getClase().getId();
			return redirect;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			
			return "alumno/index";
		}

	}
	/// --US07--->Fin----US07--\\\\\

}
