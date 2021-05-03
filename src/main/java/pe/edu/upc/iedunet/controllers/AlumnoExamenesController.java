package pe.edu.upc.iedunet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.services.ActividadAcademicaService;
import pe.edu.upc.iedunet.services.ClaseService;
import pe.edu.upc.iedunet.viewmodels.ExamenViewModel;

@Controller
@RequestMapping("/alumno/cursos/examenes")
public class AlumnoExamenesController {
	@Autowired
	private ActividadAcademicaService actividadAcademicaService;
	
	@Autowired
	private ClaseService claseService;

	
	@GetMapping()
	public String viewExamen(@RequestParam(name = "id", required = false) int id, Model model) {
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
			else {
				Clase clase = claseService.findById(id).get();
				nombreCurso = clase.getCurso().getNombre();
				grado = clase.getGrado();
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
		return "alumno/cursos/examenes/index";
	}

	@GetMapping("examen") // ?id={id}
	public String examen(@RequestParam("id") int id, Model model) {
		System.err.println("Hola, si entre xD");
		try {
			ExamenViewModel exam = actividadAcademicaService.findExam(id);

			model.addAttribute("exam", exam);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "alumno/cursos/examenes/examen";
	}
}
