package pate_d_or.equipe.bll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pate_d_or.equipe.dal.ReservationDAO;
import pate_d_or.equipe.entities.Reservation;

@Service
public class ReservationBLL 
{
	@Autowired
	private ReservationDAO reservationDAO;
	
	//-----------------------------------------
	
	public List<Reservation> findAll()
	{
		return (List<Reservation>) this.reservationDAO.findAll();
	}	
	
	//-----------------------------------------
	
	public List<Reservation> findAllByRestaurantId(int id)
	{
		return (List<Reservation>) this.reservationDAO.findAllByRestaurantId(id);
	}
	
	//-----------------------------------------
	
	public Reservation findById(int id) throws BLLException
	{
		BLLException bll = new BLLException();
		
		if(this.reservationDAO.findById(id).isEmpty())
		{
			bll.addError("reservation", "Resservation inconue");
			throw bll;
		}
		
		return this.reservationDAO.findById(id).get();
	}
	
	//-----------------------------------------
	
	public void save(Reservation reservation)
	{
		this.reservationDAO.save(reservation);
	}

}
