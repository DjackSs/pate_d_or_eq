package pate_d_or.equipe.dal;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pate_d_or.equipe.entities.User;

public interface UserDAO extends CrudRepository<User, Integer> {

	/*
	 * Nous permettra de retrouver un utilisateur d'après son token d'identification,
	 * à condition que le token n'ait pas expiré.
	 * Cette méthode est utilisée pour retrouver un utilisateur qui s'est déjà connecté.
	 */
	public User findByTokenAndExpirationTimeAfter(String token, LocalDateTime expirationTime);
	
	public List<User> findByEmail(String email);
	
}
