package pate_d_or.equipe.bll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pate_d_or.equipe.dal.ReservationDAO;
import pate_d_or.equipe.entities.Reservation;

@Service
public class ReservationBLL 
{
	private static final List<String> RESERVATION_STATES = new ArrayList<>(Arrays.asList("hold", "gran", "deni", "here"));
	
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
			bll.addError("reservation", "Reservation inconue");
			throw bll;
		}
		
		return this.reservationDAO.findById(id).get();
		
	}
	
	//-----------------------------------------
	
	public void update(Reservation reservation, int id) throws BLLException
	{
		
		BLLException bll = new BLLException();
		
		Reservation updateReservation = null;
		
		try 
		{
			updateReservation = this.findById(id);
			
		} 
		catch (BLLException error) 
		{
			bll.addError("reservation", "Reservation inconue");
		}
		
		if(!RESERVATION_STATES.contains(reservation.getState()))
		{
			bll.addError("reservationState", "Etat de réservation invalide");
		}
		
		if(bll.getErrors().size() != 0)
		{
			throw bll;
		}
				
		updateReservation.setState(reservation.getState());
		
		//si le client est arrivé alors on changge aussi le status de sa table
		if("here".equals(reservation.getState()))
		{
			updateReservation.getTable().setState("pres");
		}
		
		this.reservationDAO.save(updateReservation);
		
		
	}

}
