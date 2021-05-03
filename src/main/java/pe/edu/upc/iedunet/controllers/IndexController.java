package pe.edu.upc.iedunet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.edu.upc.iedunet.helpers.UserTesting;

@Controller
@RequestMapping("/")
public class IndexController {
	@GetMapping
	public String index(Model model) {

		return "index";
	}
	@GetMapping("login")
	public String login() {
		return "login";
	}
	
	@GetMapping("redirect")
	public String redirect() {
		Boolean isAlumno = UserTesting.Users.isAlumno();
		
		if(isAlumno) {
			return "redirect:alumno/cursos/";
		}
		else {
			return "redirect:profesor/cursos/";
		}
		
		//return "redirect:alumno/";
	}
	
	@GetMapping("test")
	public String test() {
		return "test";
	}
}
