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
@RequestMapping("/users")
public class UserRest {

	@Autowired private UserBLL userbll;

	@GetMapping
	public Iterable<User> getAllUsers() {
		return userbll.getAllUsers();
	}

	@GetMapping("/{id}")
	public User getUserById(@PathVariable("id") int id) {
		return userbll.getUserById(id);
	}

	@GetMapping("/{email}")
	public User getUserByEmailAndPassword(@PathVariable("email") String email, String password) {
		return userbll.getUserByEmailAndAdress(email, password);
	}
	

	@PostMapping
	public ResponseEntity<Void> insertUser(@RequestBody User user) {
		userbll.saveOrUpdate(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateUser(@PathVariable("id") int id, @RequestBody User user) {
		user.setId(id);
		userbll.saveOrUpdate(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(int id) {
		userbll.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}