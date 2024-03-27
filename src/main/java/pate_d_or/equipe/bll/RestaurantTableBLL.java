package pate_d_or.equipe.bll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pate_d_or.equipe.dal.RestaurantTableDAO;
import pate_d_or.equipe.entities.RestaurantTable;

@Service
public class RestaurantTableBLL 
{
	@Autowired
	private RestaurantTableDAO restaurantTableDAO;
	
	//-----------------------------------------
	
	public List<RestaurantTable> findAll()
	{
		return (List<RestaurantTable>) this.restaurantTableDAO.findAll();
	}
	
	//-----------------------------------------
	
	public RestaurantTable findById(int id)
	{
		return this.restaurantTableDAO.findById(id).get();
	}
	
	//-----------------------------------------
	
	public List<RestaurantTable> findByRestaurantId(int restaurantId)
	{
		return (List<RestaurantTable>) this.restaurantTableDAO.findByRestaurantId(restaurantId);
	}
	
	//-----------------------------------------
	
	public void save(RestaurantTable restaurantTable)
	{
		this.restaurantTableDAO.save(restaurantTable);
	}

}
