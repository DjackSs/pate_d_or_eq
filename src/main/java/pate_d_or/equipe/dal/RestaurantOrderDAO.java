package pate_d_or.equipe.dal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import pate_d_or.equipe.entities.RestaurantOrder;

public interface RestaurantOrderDAO extends CrudRepository<RestaurantOrder, Integer> 
{
	@Query( value = "select sum(Dishes.price) as bill from Dishes\r\n"
			+ "inner join Orders_Dishes on Orders_Dishes.id_dish = Dishes.id\r\n"
			+ "inner join Orders on Orders.id = Orders_Dishes.id_order\r\n"
			+ "where Orders.id = ?1", nativeQuery = true)
	Float getOrderBillById(int id); 

}
