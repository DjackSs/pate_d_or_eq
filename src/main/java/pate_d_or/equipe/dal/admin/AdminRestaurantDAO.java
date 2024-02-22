package pate_d_or.equipe.dal.admin;

import org.springframework.data.repository.CrudRepository;

import pate_d_or.equipe.entities.admin.AdminRestaurant;

public interface AdminRestaurantDAO extends CrudRepository<AdminRestaurant, Integer> 
{

}
