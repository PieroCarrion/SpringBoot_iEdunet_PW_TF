package pe.edu.upc.iedunet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pe.edu.upc.iedunet.helpers.UserTesting;
import pe.edu.upc.iedunet.models.entities.ActividadAcademica;
import pe.edu.upc.iedunet.models.entities.Calificacion;
import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.services.ActividadAcademicaService;
import pe.edu.upc.iedunet.services.ClaseService;
import pe.edu.upc.iedunet.viewmodels.CursoViewModel;
import pe.edu.upc.iedunet.viewmodels.ExamenViewModel;
import pe.edu.upc.iedunet.viewmodels.ReporteViewModel;

@Controller
@RequestMapping("/profesor/cursos/calificaciones")
public class ProfesorCalificacionesController {

/////////////////////
/////////////////////
//SECCION CALIFICACIONES CURSOS////
/////////////////////
/////////////////////
	@Autowired
	private ActividadAcademicaService actividadAcademicaService;
	
	@Autowired
	private ClaseService claseService;
	
	@GetMapping
	public String viewActividades(@RequestParam(name = "id", required = false) int id, Model model) {
		try {
			System.out.println("SI Hola como estas");
			int idClase = id;
            String nombreCurso = "none";
            String grado = "none";
			List<ActividadAcademica> actividades = actividadAcademicaService.findByClase(id);
			if (actividades.size() > 0) {
                nombreCurso = actividades.get(0).getClase().getCurso().getNombre();
                grado = actividades.get(0).getClase().getGrado();
            }
			model.addAttribute("actividades", actividades);
			model.addAttribute("nombreCurso", nombreCurso);
            model.addAttribute("grado", grado);
            model.addAttribute("idClase", idClase);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "profesor/cursos/calificaciones/index";
	}
	
	@GetMapping("verreportes")
	public String viewReportes(@RequestParam(name = "id", required = false) int id, Model model) {
		try {
			System.out.println("SI Hola como estas" + "" +id);
            String nombreCurso = "none";
            String grado = "none";
			List<ReporteViewModel> calificaciones = actividadAcademicaService.findAAbyClase(id);
			Clase claseOwner = claseService.findById(calificaciones.get(0).getIdClase()).get();
			int idClase = claseOwner.getId();
			if (calificaciones.size() > 0) {
                nombreCurso = claseOwner.getCurso().getNombre();
                grado = claseOwner.getGrado();
            }
			model.addAttribute("calificaciones", calificaciones);
			model.addAttribute("nombreCurso", nombreCurso);
            model.addAttribute("grado", grado);
            model.addAttribute("idClase", idClase);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "profesor/cursos/calificaciones/verreportes";
	}
	
	
	/*
	@GetMapping()
	public String viewExamen(@RequestParam(name = "id", required = false) int id, Model model) {
		System.err.println("examenes de la clase: " +id);
		try {
			List<ExamenViewModel> list = actividadAcademicaService.findExamsByClase(id);


			Boolean hasExams = false;
			int idClase = id;
			String nombreCurso="none";
			String grado = "none";
	
			if(list.size()>0) {
				hasExams = true;
				nombreCurso = list.get(0).getCurso();
				grado = list.get(0).getGrado();
			}	
			
			model.addAttribute("nombreCurso",nombreCurso);
			model.addAttribute("grado",grado);
			model.addAttribute("hasExams",hasExams);
			model.addAttribute("idClase",idClase);
			model.addAttribute("exams", list);


		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}

		return "/profesor/cursos/examenes/index";
	}
	/*
	@GetMapping("info/index")
	public String viewCurso(@RequestParam(name = "id", required = false) int id, Model model) {

		try {
			System.out.println("ID de la clase: " + id);
			
			
			CursoViewModel curso = alumnoService.findCurso(UserTesting.Users.getIdAlumno(),id);// ACA SERIA CLASESERVICE.FINDBYID
			int idClase = id;
			String nombreCurso="none";
			String grado = "none";
			
			if (curso != null) {
				model.addAttribute("curso", curso);
				nombreCurso = curso.getNombreCurso();
				grado = curso.getGrado();
			}
			else {
				return "/alumno/cursos/";
			}
			
			model.addAttribute("nombreCurso",nombreCurso);
			model.addAttribute("grado",grado);
			model.addAttribute("idClase",idClase);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/alumno/cursos/info/index";
	}*/

/*
	@PostMapping("calificacion")
	public String save(@ModelAttribute("calificacion") ExamenViewModel calificacion, SessionStatus status) {
		try {
//Clase en cuestion
			Clase clase = new Clase();
			clase.setId(1);

			System.out.println("En la DB se guardara: " + calificacion.getDescripcion());

			ActividadAcademica calificacion = new ActividadAcademica();
			calificacion.setClase(clase);
			calificacion.setDescripcion(calificacion.getDescripcion());
			Date current = new Date();
			calificacion.setFechaPublicacion(current);

			actividadAcademicaService.save(calificacion);
//aca se guardaria
			status.setComplete();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
// Devuelve la URL mapping 
		return "/profesor/cursos/calificaciones/addcalificacion";
	}
*/

	/*
	 * @PostMapping("calificacion") public String
	 * save(@ModelAttribute("calificacion") ExamenViewModel calificacion,
	 * SessionStatus status) { try { //Clase en cuestion Clase clase = new Clase();
	 * clase.setId(1);
	 * 
	 * System.out.println("En la DB se guardara: " + calificacion.getDescripcion());
	 * 
	 * ActividadAcademica calificacion = new ActividadAcademica();
	 * calificacion.setClase(clase);
	 * calificacion.setDescripcion(calificacion.getDescripcion()); Date current =
	 * new Date(); calificacion.setFechaPublicacion(current);
	 * 
	 * 
	 * actividadAcademicaService.save(calificacion); //aca se guardaria
	 * status.setComplete(); } catch (Exception e) { e.printStackTrace();
	 * System.err.println(e.getMessage()); } // Devuelve la URL mapping return
	 * "/profesor/cursos/calificaciones/addcalificacion"; }
	 */


}
