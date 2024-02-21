package pate_d_or.equipe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pate_d_or.equipe.bll.UserBLL;
import pate_d_or.equipe.entities.User;

@RestController
@CrossOrigin
@RequestMapping("/pate_d_or")
public class UserRest {

	@Autowired private UserBLL userbll;

	@GetMapping("/users")
	public Iterable<User> getAllUsers() {
		return userbll.getAllUsers();
	}

	@GetMapping("/users/{id}")
	public User getUserById(@PathVariable("id") int id) {
		return userbll.getUserById(id);
	}

	@PostMapping("/users/{id}")
	public ResponseEntity<User> insertUser(@PathVariable("id") int id, @RequestBody User user) {
		User operator = userbll.getUserById(id);
		if ("admi".equals(operator.getRole())) {
			userbll.saveOrUpdate(user);
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<Void> updateUser(@PathVariable("id") int id, @RequestBody User user) {
		user.setId(id);
		userbll.saveOrUpdate(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> delete(int id) {
		userbll.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}