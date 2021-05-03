 package pe.edu.upc.iedunet.controllers;
 

 import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import pe.edu.upc.iedunet.services.ProfesorService;
import pe.edu.upc.iedunet.viewmodels.CalificacionViewModel;
import pe.edu.upc.iedunet.viewmodels.CursoViewModel;

 import pe.edu.upc.iedunet.services.ActividadAcademicaService;
import pe.edu.upc.iedunet.services.ClaseService;
import pe.edu.upc.iedunet.viewmodels.ExamenViewModel;
 import pe.edu.upc.iedunet.viewmodels.TareaViewModel;
 
 
 @Controller
 @RequestMapping("/profesor/cursos/tareas")
 public class ProfesorTareasController {
	 @Autowired
		private ActividadAcademicaService actividadAcademicaService;
		@Autowired
		private ClaseService claseService;

		@GetMapping()
		public String viewTareas(@RequestParam(name = "id", required = false) int id, Model model) {
			TareaViewModel newTarea = new TareaViewModel();
			newTarea.setIdClase(id);
			System.err.println("examenes de la clase: " + id);
			try {

				List<TareaViewModel> list = actividadAcademicaService.findAllTareas(id);

				Boolean hasTareas = false;
				int idClase = id;
				String nombreCurso = "none";
				String grado = "none";

				if (list.size() > 0) {
					hasTareas = true;
					nombreCurso = list.get(0).getCurso();
					grado = list.get(0).getGrado();
				}

				model.addAttribute("nombreCurso", nombreCurso);
				model.addAttribute("grado", grado);
				model.addAttribute("hasTareas", hasTareas);
				model.addAttribute("idClase", idClase);
				model.addAttribute("tareas", list);

				model.addAttribute("tarea", newTarea);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
			return "profesor/cursos/tareas/index";
		}
		
		

		@PostMapping("save-tarea")
		public String save(@ModelAttribute("tarea") TareaViewModel tarean, SessionStatus status) {
			try {
	//Clase en cuestion
				Clase clase = new Clase();
				clase.setId(tarean.getIdClase());

				System.err.println("En la DB se guardara: " + tarean.getDescripcion() + " de la clase "
						+ tarean.getIdClase() + " con link " + tarean.getLink());

				ActividadAcademica tarea = new ActividadAcademica();
				tarea.setClase(clase);

				tarea.setDescripcion(tarean.getFullDescripcion());
				Date current = new Date();
				tarea.setFechaPublicacion(current);
				tarea.setFechaMaxEntrega(tarean.getFechaMaxEntrega());

				actividadAcademicaService.save(tarea);

				status.setComplete();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}

			String redirect = "redirect:?id=" + tarean.getIdClase();
			return redirect;
		}

		@GetMapping("viewtareas")
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
			return "profesor/cursos/tareas/viewtareas";
		}

		@GetMapping("scoretarea")
		public String viewAlumnoCalificacion(@RequestParam(name = "idA", required = false) int idA,@RequestParam(name = "idC", required = false) int idC, Model model) {
			try {
				CalificacionViewModel newCalificacion = new CalificacionViewModel();
				
				Boolean canCheck = false;
			
				newCalificacion.setIdAlumno(idA);
				newCalificacion.setIdExamen(idC);
				
				Alumno objAlumno = new Alumno();
				objAlumno.setId(idA);
				ActividadAcademica actividadAcademica = new ActividadAcademica();
				actividadAcademica.setId(idC);
				
				Optional<Calificacion> calificacion = actividadAcademicaService.getCalificacionRepository().findByAlumnoAndActividadAcademica(objAlumno,actividadAcademica);
				
				if(calificacion.isPresent()) {
					newCalificacion.setLink(calificacion.get().getLinkDoc());
					
					canCheck = true;
					
					System.err.println("prueba en el get"+newCalificacion.getIdCalificacion());
					model.addAttribute("calificacion", newCalificacion);
				}				

				model.addAttribute("canCheck", canCheck);

			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
			return "profesor/cursos/tareas/scoretarea";
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

			String redirect = "redirect:viewtareas?id=" + calificacion.getIdExamen();
			return redirect;

		}

	}

