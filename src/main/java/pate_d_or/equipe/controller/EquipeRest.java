package pate_d_or.equipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pate_d_or.equipe.bll.ReservationBLL;
import pate_d_or.equipe.entities.Reservation;

@RestController
@CrossOrigin
@RequestMapping("/pate_d_or")
public class EquipeRest 
{
	@Autowired
	private ReservationBLL reservationBLL;
	
	//=====================================================
	//reservation
	
	@GetMapping("/resa")
	public ResponseEntity<List<Reservation>> finAll()
	{
		return new ResponseEntity<>(this.reservationBLL.findAll(), HttpStatus.OK);
	}
	
	//-----------------------------------------
	
	@GetMapping("/resa/{id}")
	public ResponseEntity<Reservation> finAll(@PathVariable("id") int id)
	{
		return new ResponseEntity<>(this.reservationBLL.findById(id), HttpStatus.OK);
	}
	
	//-----------------------------------------
	
	@PutMapping("/resa/{id}")
	public ResponseEntity<Void> updateReservation(@PathVariable("id") int id, @RequestBody Reservation reservation)
	{
		Reservation updateReservation = this.reservationBLL.findById(id);
		
		//Change uniquement le status de la réservation
		updateReservation.setState(reservation.getState());
		
		this.reservationBLL.save(updateReservation);
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	//-----------------------------------------
	
	
	

}
