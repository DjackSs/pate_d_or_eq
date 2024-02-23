package pate_d_or.equipe.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pate_d_or.equipe.bll.BLLException;
import pate_d_or.equipe.bll.ReservationBLL;
import pate_d_or.equipe.bll.RestaurantOrderBLL;
import pate_d_or.equipe.bll.RestaurantTableBLL;
import pate_d_or.equipe.bll.UserBLL;
import pate_d_or.equipe.entities.Reservation;
import pate_d_or.equipe.entities.RestaurantOrder;
import pate_d_or.equipe.entities.RestaurantTable;
import pate_d_or.equipe.entities.User;


@RestController
@CrossOrigin
@RequestMapping("/pate_d_or")
public class EquipeRest 
{
	@Autowired
	private ReservationBLL reservationBLL;
	
	@Autowired
	private RestaurantTableBLL retaurantTableBLL;
	
	@Autowired
	private UserBLL userBLL;
	
	@Autowired 
	private RestaurantOrderBLL restaurantOrderBll;
	
	//=====================================================
	//reservation
	
	@GetMapping("/resa")
	public ResponseEntity<List<Reservation>> findAll() {
		return new ResponseEntity<>(this.reservationBLL.findAll(), HttpStatus.OK);
	}
	
	//-----------------------------------------
	
	@GetMapping("/resa/{id}")
	public ResponseEntity<Reservation> findResaById(@PathVariable("id") int id) {
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
	public ResponseEntity<List<RestaurantTable>> findAllTables()
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
	
	//=====================================================
	//User
	
	@GetMapping("/users")
	public Iterable<User> getAllUsers() throws BLLException {
		return userBLL.getAllUsers();
	}

	@GetMapping("/users/{id}")
	public User getUserById(@PathVariable("id") int id) {
		return userBLL.getUserById(id);
	}

	@PostMapping("/users")
	public ResponseEntity<User> insertUser(@RequestBody User user) throws BLLException {
		
		if ("staf".equals(user.getRole())) 
		{
			try 
			{
				userBLL.saveOrUpdate(user);
			} 
			catch (BLLException e) 
			{
				
				throw new BLLException("Impossible de créer un nouvel utilisateur", e);
			}
			
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<Void> updateUser(@PathVariable("id") int id, @RequestBody User user) throws BLLException {
		user.setId(id);
		try {
			userBLL.saveOrUpdate(user);
		} catch (BLLException e) {
			throw new BLLException("Impossible de mettre à jour l'utilisateur", e);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(int id) {
		userBLL.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//=====================================================
	//restaurantOrder
	
	@GetMapping("/commandes")
	public List<RestaurantOrder> getAll() {
		return restaurantOrderBll.getAll();
	}
	
		
	@GetMapping("/commandes/{id}")
	public ResponseEntity<RestaurantOrder> getById(@PathVariable("id") int id) {
		return new ResponseEntity<>(restaurantOrderBll.getById(id), HttpStatus.OK);
	}
	
	
	@GetMapping("/commandes/bill/{id}")
	public ResponseEntity<Float> getOrderBillById(@PathVariable("id") int id)
	{
		return new ResponseEntity<>(this.restaurantOrderBll.getOrderBillById(id), HttpStatus.OK);
	}
	
	@PostMapping("/commandes")
	public ResponseEntity<RestaurantOrder> insert(@RequestBody RestaurantOrder restaurantOrder) {
		restaurantOrderBll.save(restaurantOrder);
		return new ResponseEntity<>(restaurantOrder, HttpStatus.CREATED);
	}
	
	@PutMapping("/commandes/{id}/modifier-etat")
	public ResponseEntity<Void> updateState(@PathVariable("id") int id, @RequestBody RestaurantOrder restaurantOrder) {
		RestaurantOrder restaurantOrderToUpdate = restaurantOrderBll.getById(id);
		restaurantOrderToUpdate.setState(restaurantOrder.getState());
		restaurantOrderBll.save(restaurantOrderToUpdate);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/commandes/{id}/ajouter-plats")
	public ResponseEntity<Void> updateDishes(@PathVariable("id") int id, @RequestBody RestaurantOrder restaurantOrder) {
		//RestaurantOrder restaurantOrderToUpdate = restaurantOrderBll.getById(id);
		restaurantOrderBll.updateDishes(id, restaurantOrder);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/commandes/{id}")
	public ResponseEntity<RestaurantOrder> deleteOrder(@PathVariable("id") int id) {
		RestaurantOrder restaurantOrder = restaurantOrderBll.getById(id);
		restaurantOrderBll.delete(id);
		return new ResponseEntity<>(restaurantOrder, HttpStatus.OK);
	}
	
}
	

