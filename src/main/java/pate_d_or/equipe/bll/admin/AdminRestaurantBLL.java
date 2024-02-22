package pate_d_or.equipe.bll.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pate_d_or.equipe.dal.admin.AdminRestaurantDAO;
import pate_d_or.equipe.entities.admin.AdminRestaurant;

@Service
public class AdminRestaurantBLL 
{
	@Autowired
	private AdminRestaurantDAO adminRestaurantDAO;
	
	public List<AdminRestaurant> findAll()
	{
		return (List<AdminRestaurant>) this.adminRestaurantDAO.findAll();
	}
	
	public void save(AdminRestaurant newRestaurant)
	{
		this.adminRestaurantDAO.save(newRestaurant);
	}
	
	

}
