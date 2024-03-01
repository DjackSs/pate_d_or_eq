package pate_d_or.equipe.bll;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import pate_d_or.equipe.dal.UserDAO;
import pate_d_or.equipe.entities.User;

@Service
public class UserBLL {
	@Autowired private UserDAO userDAO;
	
	private static final int MIN_LENGTH = 2;
	
	//------------------user constants
	private static final int USER_NAME_MAX_LENGTH = 40;
	private static final int USER_LASTNAME_MAX_LENGTH = 40;
	private static final int USER_EMAIL_MAX_LENGTH = 60;
	private static final int USER_PASSWORD_MAX_LENGTH = 60;
	private static final String EMAIL_REGEX = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
	//The password must contain at least one lowercase character, one uppercase character, one digit, one special character, and a length between 4 to 20.
	//https://mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
	private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{4,20}$";
	
	//token
	private  final SecureRandom secureRandom = new SecureRandom();
	private  final Encoder base64encoder = Base64.getUrlEncoder();
	private static final int USER_TOKEN_LIFETIME = 30;
	
	
	//====================================================================
	
	public List<User> getAllUsers() {
	
		return (List<User>) userDAO.findAll();
	
	}
	
	//--------------------------------------------------------------------
	
	public User getUserById(int id) throws BLLException
	{
		BLLException bll = new BLLException();
		
		if(userDAO.findById(id).isEmpty())
		{
			bll.addError("user", "Utilisateur inconue");
			throw bll;
		}
		
		return userDAO.findById(id).get();
	}
	
	//--------------------------------------------------------------------
	
	public User getByLoginAndPassword(String email, String password) throws BLLException {
		
		BLLException bll = new BLLException();
		
		//email
		if(StringUtils.isBlank(email)) {
			bll.addError("emailSize", "Veuillez saisir une adresse mail");
		}
		
		//password
		if(StringUtils.isBlank(password)) {
			bll.addError("password", "Mot de passe invalide");
				
		}
		
		if(bll.getErrors().size() != 0) {
			throw bll;
		}
			
		List<User> users = userDAO.findByEmail(email);
		User trueUser = null;
		
		for(User user : users)
		{
			if(BCrypt.checkpw(password, user.getPassword()))
			{
				trueUser = user;
			}
		}
		
		if (trueUser != null) {
			trueUser.setToken(generateToken());
			trueUser.setExpirationTime(LocalDateTime.now().plusMinutes(USER_TOKEN_LIFETIME));
			userDAO.save(trueUser);
		}
		
		return trueUser;
	
	}
	
	//--------------------------------------------------------------------
	
	/*
	 * Quand on s'identifie avec le token, on en profite pour mettre à jour
	 * la date d'expiration du token. Ainsi, tant que l'utilisateur est actif
	 * sur l'application, le token n'expire pas.
	 */
	
	public User getByToken(String token) {
		User user = userDAO.findByTokenAndExpirationTimeAfter(token, LocalDateTime.now());
		if (user != null) {
			user.setExpirationTime(LocalDateTime.now().plusMinutes(USER_TOKEN_LIFETIME));
			userDAO.save(user);
		}
		return user;
	}
	
	//--------------------------------------------------------------------
	
	public void logout(String token) {
		User user = userDAO.findByTokenAndExpirationTimeAfter(token, LocalDateTime.now());
		if (user != null) {
			user.setToken(null);
			user.setExpirationTime(null);
			userDAO.save(user);
		}
	}
	
	//--------------------------------------------------------------------
	
	public void saveOrUpdate(User user) throws BLLException {
		BLLException bll = new BLLException ();
		
		//name
		if(!StringUtils.isBlank(user.getName())) {
			if(user.getName().trim().length() > USER_NAME_MAX_LENGTH) {
				bll.addError("nameSize", "Votre prénom est trop long");
						
			}
			
			if(user.getName().trim().length() < MIN_LENGTH) {
				bll.addError("nameSize", "Votre prénom est trop court");
				
			}
			
		} else {
			bll.addError("nameSize", "Veuillez saisir un prénom");
		}
		
		
		//lastname
		if(!StringUtils.isBlank(user.getLastname())) {
			if(user.getLastname().trim().length() > USER_LASTNAME_MAX_LENGTH) {
				bll.addError("lastnameSize", "Votre nom est trop long");
						
			}
			
			if(user.getLastname().trim().length() < MIN_LENGTH) {
				bll.addError("lastnameSize", "Votre nom est trop court");
				
			}
			
		} else {
			bll.addError("lastnameSize", "Veuillez saisir un nom");
		}
		
		
		
		//email
		if(!StringUtils.isBlank(user.getEmail())) {
			if(user.getEmail().trim().length() > USER_EMAIL_MAX_LENGTH) {
				bll.addError("emailSize", "Votre adresse mail est trop longue");
						
			}
			
			if(user.getEmail().trim().length() < MIN_LENGTH) {
				bll.addError("emailSize", "Votre adresse mail est trop courte");
				
			}
			
			if(!this.regexMatche(user.getEmail(), EMAIL_REGEX)) {
				bll.addError("emailMatch", "Votre adresse est invalide");
			}
			
		} else {
			bll.addError("emailSize", "Veuillez saisir une adresse mail");
		}
		
		
		//password
		if(!StringUtils.isBlank(user.getPassword())) {
			if(user.getPassword().trim().length() > USER_PASSWORD_MAX_LENGTH) {
				bll.addError("password", "Mot de passe invalide");
						
			}
			
			if(user.getPassword().trim().length() < MIN_LENGTH) {
				bll.addError("password", "Mot de passe invalide");
				
			}
			
			if(!this.regexMatche(user.getPassword(), PASSWORD_REGEX)) {
				bll.addError("password", "Mot de passe invalide");
			}
				
		} else {
			bll.addError("password", "Mot de passe invalide");
		}
		
		
		if(bll.getErrors().size() != 0) {
			throw bll;
		}
		
		//hashing the password
		user.setPassword(this.toHash(user.getPassword()));
		
	
		userDAO.save(user);
		
	}
	
	//--------------------------------------------------------------------
	
	public void deleteById(int id) {
		userDAO.deleteById(id);
	}
	
	//--------------------------------------------------------------------
	
	//regex test method
	private boolean regexMatche(String test, String regexPattern)
	{
		return Pattern.compile(regexPattern).matcher(test).matches();
	}
	
	//--------------------------------------------------------------------
	
	private String toHash(String password)
	{
		//gensalt() augmente la complexité du cryptage, gensalt(10) par défaut, varie de 4 à 31
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
	//--------------------------------------------------------------------
	
	private String generateToken() {
		byte[] randomBytes = new byte[48];
		secureRandom.nextBytes(randomBytes);
		return base64encoder.encodeToString(randomBytes);
	}
	
}
