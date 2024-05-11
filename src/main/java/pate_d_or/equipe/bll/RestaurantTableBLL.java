package pate_d_or.equipe.bll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pate_d_or.equipe.dal.RestaurantTableDAO;
import pate_d_or.equipe.entities.RestaurantTable;

@Service
public class RestaurantTableBLL 
{
	private static final List<String> RESTAURANT_TABLE_STATE = new ArrayList<>(Arrays.asList("pres", null));
	
	@Autowired
	private RestaurantTableDAO restaurantTableDAO;
	
	//-----------------------------------------
	
	public List<RestaurantTable> findAll()
	{
		return (List<RestaurantTable>) this.restaurantTableDAO.findAll();
	}
	
	//-----------------------------------------
	
	public RestaurantTable findById(int id) throws BLLException
	{
		BLLException bll = new BLLException();
		
		if(this.restaurantTableDAO.findById(id).isEmpty()) 
		{
			bll.addError("table", "Table inconue");
			throw bll;
			
		}
		
		return this.restaurantTableDAO.findById(id).get();
	}
	
	//-----------------------------------------
	
	public List<RestaurantTable> findByRestaurantId(int restaurantId)
	{
		return (List<RestaurantTable>) this.restaurantTableDAO.findByRestaurantId(restaurantId);
	}
	
	//-----------------------------------------
	
	public void update(RestaurantTable restaurantTable, int id) throws BLLException
	{
		BLLException bll = new BLLException();
		
		RestaurantTable updateRestaurantTable = null;
		
		try
		{
			updateRestaurantTable = this.findById(id);
		}
		catch(BLLException error)
		{
			bll.addError("table", "Table inconue");
		}
		
		if(!RESTAURANT_TABLE_STATE.contains(restaurantTable.getState()))
		{
			bll.addError("tableState", "Etat de table invalide");
		}
		
		if(bll.getErrors().size() != 0)
		{
			throw bll;
		}
		
		updateRestaurantTable.setState(restaurantTable.getState());
		
		this.restaurantTableDAO.save(updateRestaurantTable);
	}

}
