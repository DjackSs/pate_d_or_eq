package pate_d_or.equipe.dal;

import org.springframework.data.repository.CrudRepository;

import pate_d_or.equipe.entities.RestaurantTable;

public interface RestaurantTableDAO extends CrudRepository<RestaurantTable, Integer> 
{

}
