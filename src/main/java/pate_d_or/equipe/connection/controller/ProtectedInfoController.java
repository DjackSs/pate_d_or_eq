package pate_d_or.equipe.connection.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/protected")
public class ProtectedInfoController {
	@GetMapping
	public String get() {
		return "Une info super confidentielle";
	}
}
