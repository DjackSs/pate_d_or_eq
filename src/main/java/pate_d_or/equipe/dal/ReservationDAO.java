package pate_d_or.equipe.dal;

import org.springframework.data.repository.CrudRepository;

import pate_d_or.equipe.entities.Reservation;

public interface ReservationDAO extends CrudRepository<Reservation, Integer> 
{

}
