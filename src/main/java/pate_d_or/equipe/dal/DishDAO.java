package pate_d_or.equipe.dal;

import org.springframework.data.repository.CrudRepository;

import pate_d_or.equipe.entities.Dish;

public interface DishDAO extends CrudRepository<Dish, Integer>{

}
