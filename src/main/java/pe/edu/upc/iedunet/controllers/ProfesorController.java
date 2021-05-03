package pe.edu.upc.iedunet.controllers;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


import pe.edu.upc.iedunet.models.entities.Profesor;
import pe.edu.upc.iedunet.services.ProfesorService;
import pe.edu.upc.iedunet.viewmodels.HorarioItemViewModel;
import pe.edu.upc.iedunet.viewmodels.HorarioViewModel;
import pe.edu.upc.iedunet.helpers.HorarioGenerator;
import pe.edu.upc.iedunet.helpers.UserTesting;


@Controller
@RequestMapping("/profesor")
@SessionAttributes("exam")
public class ProfesorController {
//se le pasa el id de la clase o la clase en si y los datos de la eva/
//en la descripcion de guarda el link de la eva y un peque;o texto

	@Autowired
	private ProfesorService profesorService;

	// Para obtener data de la BD y enviarlo al Front
	@GetMapping
	public String inicio(Model model) {
		try {
			List<Profesor> profesores = profesorService.findAll();
			model.addAttribute("profesores", profesores);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		// Devuelve el nombre del archivo HTML
		return "profesor/index";
		//return "redirect:cursos";
	}

	// Para generar el horario
	@GetMapping("horario")
	public String horario(Model model) {
		HorarioGenerator horarioGenerator = new HorarioGenerator();

		try {
			// ACA SE OBTENDRIA EL Profesor EN SESION PARA MOSTRAR SU HORARIO
			List<HorarioViewModel> horarios = profesorService.findHorario(UserTesting.Users.getIdProfesor());

			// Se pasa la lista de profesores al Generator para formatear para mostrar en el
			// Table
			List<HorarioItemViewModel> horariositems = horarioGenerator.GenerateHorario(horarios);

			model.addAttribute("horariositems", horariositems);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		} // Devuelve el nombre del archivo HTML
		return "profesor/horario";
	}

	@GetMapping("calificaciones")
	public String calificaciones(Model model) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "profesor/calificaciones";
	}

}
