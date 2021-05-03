package pe.edu.upc.iedunet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pe.edu.upc.iedunet.services.RecursosService;
import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.models.entities.Recurso;
import pe.edu.upc.iedunet.services.ActividadAcademicaService;
import pe.edu.upc.iedunet.services.ClaseService;
import pe.edu.upc.iedunet.viewmodels.ExamenViewModel;

@Controller
@RequestMapping("/alumno/cursos/recursos")
public class AlumnoRecursosController {

	@Autowired
	private RecursosService recursoService;

	private ActividadAcademicaService actividadAcademicaService;
	@Autowired
	private ClaseService claseService;

	@GetMapping()
	public String viewRecursos(@RequestParam(name = "id", required = false) int id, Model model) {
		System.err.println("recursos de la clase: " + id);
		try {
			System.err.print("HOLA RECURSO");

			int idClase = id;
			String nombreCurso = "none";
			String grado = "none";

			Clase clase = claseService.findById(id).get();
			nombreCurso = clase.getCurso().getNombre();
			grado = clase.getGrado();
			List<Recurso> recursos = recursoService.findAllByClase(idClase);
			if(recursos.size()>0) {
				model.addAttribute("recursos", recursos);
			}
			model.addAttribute("nombreCurso", nombreCurso);
			model.addAttribute("grado", grado);
			model.addAttribute("idClase", idClase);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "alumno/cursos/recursos/index";
	}
}
