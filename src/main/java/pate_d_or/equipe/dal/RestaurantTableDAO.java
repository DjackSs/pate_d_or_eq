package pate_d_or.equipe.dal;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pate_d_or.equipe.entities.RestaurantTable;

public interface RestaurantTableDAO extends CrudRepository<RestaurantTable, Integer> 
{
	
	public List<RestaurantTable> findByRestaurantId(int restaurantId);

}
