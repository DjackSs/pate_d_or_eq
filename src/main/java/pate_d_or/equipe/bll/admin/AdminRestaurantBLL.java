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
	
	//====================================================
	
	public List<AdminRestaurant> findAll()
	{
		return (List<AdminRestaurant>) this.adminRestaurantDAO.findAll();
	}
	
	//----------------------------------------------------
	
	public AdminRestaurant findById(int id)
	{
		return this.adminRestaurantDAO.findById(id).get();
	}
	
	//----------------------------------------------------
	
	public void save(AdminRestaurant newRestaurant)
	{
		if(newRestaurant.getSchedules().size() != 0 && newRestaurant.getTables().size() != 0)
		{
			this.adminRestaurantDAO.save(newRestaurant);
		}
		
	}
	
	//----------------------------------------------------
	
	public void delete(int id)
	{
		this.adminRestaurantDAO.deleteById(id);
	}
	

}
