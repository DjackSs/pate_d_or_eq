package pate_d_or.equipe.connection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pate_d_or.equipe.bll.BLLException;
import pate_d_or.equipe.bll.UserBLL;
import pate_d_or.equipe.dal.DALException;
import pate_d_or.equipe.entities.User;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController {
	@Autowired private UserBLL service;
	
	/*
	 * Endpoint utilisé pour authentifier un utilisateur au moment du login.
	 * Renvoie une erreur 401 "Unauthorized" si le couple identifiant / mdp est faux
	 * Renvoie un user avec son token si la connexion réussit
	 */
	@PostMapping
	public ResponseEntity<User> get(@RequestBody User user) throws BLLException {
		User userLog = service.getByLoginAndPassword(user.getEmail(), user.getPassword());
		if (userLog == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<>(userLog, HttpStatus.OK);
		}
	}
}
