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
import pate_d_or.equipe.bll.RestaurantTableBLL;
import pate_d_or.equipe.entities.Reservation;
import pate_d_or.equipe.entities.RestaurantTable;

@RestController
@CrossOrigin
@RequestMapping("/pate_d_or")
public class EquipeRest 
{
	@Autowired
	private ReservationBLL reservationBLL;
	
	@Autowired
	private RestaurantTableBLL retaurantTableBLL;
	
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
		
		//si le client est arrivé alors on changge aussi le status de sa table
		if("here".equalsIgnoreCase(reservation.getState()))
		{
			reservation.getTable().setState("pres");
		}
		
		this.reservationBLL.save(updateReservation);
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	
	
	//=====================================================
	//restaurantTable
	
	@GetMapping("/table")
	public ResponseEntity<List<RestaurantTable>> findAll()
	{
		return new ResponseEntity<>(this.retaurantTableBLL.findAll(), HttpStatus.OK);
	}
	
	//-----------------------------------------
	
	@GetMapping("/table/{id}")
	public ResponseEntity<RestaurantTable> findById(@PathVariable("id") int id)
	{
		return new ResponseEntity<>(this.retaurantTableBLL.findById(id), HttpStatus.OK);
	}
	
	//-----------------------------------------
	
	@PutMapping("/table/{id}")
	public ResponseEntity<Void> updateRestaurantTable(@PathVariable("id") int id, @RequestBody RestaurantTable restaurantTable)
	{
		RestaurantTable updateRestaurantTable = this.retaurantTableBLL.findById(id);
		updateRestaurantTable.setState(restaurantTable.getState());
		
		this.retaurantTableBLL.save(updateRestaurantTable);
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	

	
	

}
