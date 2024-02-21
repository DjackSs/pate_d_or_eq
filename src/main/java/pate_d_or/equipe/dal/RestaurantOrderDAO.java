package pate_d_or.equipe.dal;

import org.springframework.data.repository.CrudRepository;

import pate_d_or.equipe.entities.RestaurantOrder;

public interface RestaurantOrderDAO extends CrudRepository<RestaurantOrder, Integer> {

}
