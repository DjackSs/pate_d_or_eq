package pate_d_or.equipe.dal;

import org.springframework.data.repository.CrudRepository;

import pate_d_or.equipe.entities.User;

public interface UserDAO extends CrudRepository<User, Integer> {

	public User findByEmailAndPassword(String email, String password);
	
}
