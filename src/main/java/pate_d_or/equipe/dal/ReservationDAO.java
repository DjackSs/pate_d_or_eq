package pate_d_or.equipe.dal;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import pate_d_or.equipe.entities.Reservation;

public interface ReservationDAO extends CrudRepository<Reservation, Integer> 
{
	@Query( value = "select Reservations.* from Reservations\r\n"
			+ "inner join Tables on Reservations.id_table = Tables.id\r\n"
			+ "inner join Restaurants on Tables.id_restaurant = Restaurants.id\r\n"
			+ "where Restaurants.id = ?1", nativeQuery = true)
	List<Reservation> findAllByRestaurantId(int id);

}
