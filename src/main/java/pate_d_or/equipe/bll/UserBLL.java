package pate_d_or.equipe.bll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pate_d_or.equipe.dal.UserDAO;
import pate_d_or.equipe.entities.User;

@Service
public class UserBLL {
	@Autowired private UserDAO userdao;
	
	public Iterable<User> getAllUsers() {
		return userdao.findAll();
	}
	
	public User getUserById(int id) {
		return userdao.findById(id).get();
	}
	
	public User getUserByEmailAndAdress(String email, String password) {
		return userdao.findByEmailAndPassword(email, password);
	}
	
	public void saveOrUpdate(User user) {
		userdao.save(user);
	}
	
	public void deleteById(int id) {
		userdao.deleteById(id);
	}
}
