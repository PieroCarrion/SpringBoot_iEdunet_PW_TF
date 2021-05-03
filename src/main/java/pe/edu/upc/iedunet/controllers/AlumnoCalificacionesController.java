package pe.edu.upc.iedunet.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pe.edu.upc.iedunet.helpers.UserTesting;
import pe.edu.upc.iedunet.services.ActividadAcademicaService;
import pe.edu.upc.iedunet.services.AlumnoService;
import pe.edu.upc.iedunet.services.ClaseService;
import pe.edu.upc.iedunet.services.CursoService;
import pe.edu.upc.iedunet.viewmodels.CursoViewModel;
import pe.edu.upc.iedunet.viewmodels.ReporteViewModel;


@Controller
@RequestMapping("/alumno/cursos/calificaciones")
public class AlumnoCalificacionesController {
	
	@Autowired
	private AlumnoService alumnoService;

	@Autowired
	private CursoService cursoService;

	@Autowired
	private ClaseService claseService;

	@Autowired
	private ActividadAcademicaService actividadAcademicaService;

	@GetMapping()
	public String viewCalificaciones(@RequestParam(name = "id", required = false) int id, Model model) {
		System.err.println("calificaciones de la clase: " +id);
		try {
			System.out.println("Prueba Pruebita");
			CursoViewModel curso = alumnoService.findCurso(UserTesting.Users.getIdAlumno(), id);
			int idClase = id;
			String nombreCurso = "none";
			String grado = "none";
			List<ReporteViewModel> actividades = actividadAcademicaService.findByCalificacionAlumno(UserTesting.Users.getIdAlumno(), id);
			if (actividades.size() > 0) {
				model.addAttribute("curso", curso);
				nombreCurso = curso.getNombreCurso();
				System.err.println(nombreCurso);
				grado = curso.getGrado();
            }
			
			for (ReporteViewModel reporteViewModel : actividades) {
				System.err.println(reporteViewModel.getNombreAlumno()+ " " + reporteViewModel.getGrado()+ " " +reporteViewModel.getNota() + " " +reporteViewModel.getIdClase()+ " " + reporteViewModel.getIdActividad());
			}
			model.addAttribute("actividades", actividades);
			model.addAttribute("nombreCurso",nombreCurso);
			model.addAttribute("grado",grado);
			model.addAttribute("idClase",idClase);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "alumno/cursos/calificaciones/index";
	}
}
