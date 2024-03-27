package pate_d_or.equipe.dal;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import pate_d_or.equipe.entities.RestaurantOrder;

public interface RestaurantOrderDAO extends CrudRepository<RestaurantOrder, Integer> 
{
	List<RestaurantOrder> findByTableId(int tableId);
	
	@Query( value = "SELECT o.id_table AS table_number, d.name AS dish_name, d.price AS dish_price\r\n"
			+ "FROM Orders o\r\n"
			+ "JOIN Orders_Dishes od ON o.id = od.id_order\r\n"
			+ "JOIN Dishes d ON od.id_dish = d.id\r\n"
			+ "WHERE o.state = 'sold'\r\n"
			+ "ORDER BY o.id_table;", nativeQuery = true)
	String getDetailBillWhereStateSoldAndOrderByIdTable(int id);

	@Query( value = "select sum(Dishes.price) as bill from Dishes\r\n"
			+ "inner join Orders_Dishes on Orders_Dishes.id_dish = Dishes.id\r\n"
			+ "inner join Orders on Orders.id = Orders_Dishes.id_order\r\n"
			+ "where Orders.id = ?1", nativeQuery = true)
	Float getTotalAmountOrderBillById(int id); 
}
