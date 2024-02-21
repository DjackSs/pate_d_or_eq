package pate_d_or.equipe.bll;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pate_d_or.equipe.dal.UserDAO;
import pate_d_or.equipe.entities.User;

@Service
public class UserBLL {
	@Autowired private UserDAO userdao;
	
	/*
	 * Les attributs static suivants et la méthode generateToken
	 * sont des outils nous permettant de générer un token aléatoire
	 * de 64 caractères de long.
	 */
	private static final SecureRandom secureRandom = new SecureRandom();
	private static final Encoder base64encoder = Base64.getUrlEncoder();
	
	private String generateToken() {
		byte[] randomBytes = new byte[48];
		secureRandom.nextBytes(randomBytes);
		return base64encoder.encodeToString(randomBytes);
	}
	
	public Iterable<User> getAllUsers() {
		return userdao.findAll();
	}
	
	public User getUserById(int id) {
		return userdao.findById(id).get();
	}
	
	public User getUserByEmailAndAdress(String email, String password) {
		return userdao.findByEmailAndPassword(email, password);
	}
	
	/*
	 * Quand on se connecte avec login et mdp, on crée un token aléatoire
	 * qu'on renverra à l'utilisateur pour ses accès futurs.
	 * Ici, le token est configuré pour expirer après 30 minutes d'inactivité
	 */
	public User getByLoginAndPassword(String login, String password) {
		User user = userdao.findByNameAndPasswordIs(login, password);
		if (user != null) {
			user.setToken(generateToken());
			user.setExpirationTime(LocalDateTime.now().plusMinutes(30));
			userdao.save(user);
		}
		return user;
	}
	
	/*
	 * Quand on s'identifie avec le token, on en profite pour mettre à jour
	 * la date d'expiration du token. Ainsi, tant que l'utilisateur est actif
	 * sur l'application, le token n'expire pas.
	 */
	
	public User getByToken(String token) {
		User user = userdao.findByTokenAndExpirationTimeAfter(token, LocalDateTime.now());
		if (user != null) {
			user.setExpirationTime(LocalDateTime.now().plusMinutes(30));
			userdao.save(user);
		}
		return user;
	}
	
	public void logout(String token) {
		User user = userdao.findByTokenAndExpirationTimeAfter(token, LocalDateTime.now());
		if (user != null) {
			user.setToken(null);
			user.setExpirationTime(null);
			userdao.save(user);
		}
	}
	
	public void saveOrUpdate(User user) {
		userdao.save(user);
	}
	
	public void deleteById(int id) {
		userdao.deleteById(id);
	}
}
