package pate_d_or.equipe.controller;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pate_d_or.equipe.bll.BLLException;
import pate_d_or.equipe.bll.ReservationBLL;
import pate_d_or.equipe.bll.RestaurantOrderBLL;
import pate_d_or.equipe.bll.RestaurantTableBLL;
import pate_d_or.equipe.bll.UserBLL;
import pate_d_or.equipe.dto.BillDTO;
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
	public ResponseEntity<List<Reservation>> findAll() 
	{
		return new ResponseEntity<>(this.reservationBLL.findAll(), HttpStatus.OK);
	}
	
	//-----------------------------------------
	
	@GetMapping("/resa/{id}")
	public ResponseEntity<?> findResaById(@PathVariable("id") int id) 
	{
		try
		{
			return new ResponseEntity<Reservation>(this.reservationBLL.findById(id), HttpStatus.OK);
		}
		catch (BLLException error)
		{
			return new ResponseEntity<Map<String,String>>(error.getErrors(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	//-----------------------------------------
	
	@GetMapping("/resa/restaurant/{id}")
	public ResponseEntity<List<Reservation>> findAllByRestaurantId(@PathVariable("id") int id) 
	{
		
		return new ResponseEntity<List<Reservation>>(this.reservationBLL.findAllByRestaurantId(id), HttpStatus.OK);
		 
		
	}
	
	//-----------------------------------------
	
	@PutMapping("/resa/{id}")
	public ResponseEntity<?> updateReservation(@PathVariable("id") int id, @RequestBody Reservation reservation)
	{
		try
		{
			this.reservationBLL.update(reservation, id);
			
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (BLLException error)
		{
			for(String errorType : error.getErrors().keySet() )
			{
				if("reservationState".equals(errorType))
				{
					return new ResponseEntity<Map<String,String>>(error.getErrors(), HttpStatus.BAD_REQUEST);
					
				}
			}
			
			return new ResponseEntity<Map<String,String>>(error.getErrors(), HttpStatus.NOT_FOUND);
		}
		
		
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
	public ResponseEntity<?> findById(@PathVariable("id") int id)
	{
		try
		{
			return new ResponseEntity<RestaurantTable>(this.retaurantTableBLL.findById(id), HttpStatus.OK);
		}
		catch(BLLException error)
		{
			return new ResponseEntity<Map<String,String>>(error.getErrors(), HttpStatus.NOT_FOUND);
			
		}
		
	}
	
	public ResponseEntity<Object> restException(Map<String,String> errors, HttpStatus errorCode)
	{
		return new ResponseEntity<>(errors, errorCode);
	}
	
	
	//-----------------------------------------
	
	@GetMapping("/table/resto/{id}")
	public ResponseEntity<List<RestaurantTable>> findByRestaurantId(@PathVariable("id") int restaurantId)
	{
		return new ResponseEntity<>(this.retaurantTableBLL.findByRestaurantId(restaurantId), HttpStatus.OK);
	}
	
	//-----------------------------------------
	
	@PutMapping("/table/{id}")
	public ResponseEntity<?> updateRestaurantTable(@PathVariable("id") int id, @RequestBody RestaurantTable restaurantTable)
	{
		try
		{
			this.retaurantTableBLL.update(restaurantTable, id);
			
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(BLLException error)
		{
			for(String errorType : error.getErrors().keySet() )
			{
				if("tableState".equals(errorType))
				{
					return new ResponseEntity<Map<String,String>>(error.getErrors(), HttpStatus.BAD_REQUEST);
					
				}
			}
			
			return new ResponseEntity<Map<String,String>>(error.getErrors(), HttpStatus.NOT_FOUND);
			
		}
		
		
	}
	
	//=====================================================
	//User
	
	@GetMapping("/user")
	public ResponseEntity<List<User>> getAllUsers()
	{
		return new ResponseEntity<>(userBLL.getAllUsers(), HttpStatus.OK);
	}
	
	//-----------------------------------------

	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") int id) 
	{
		try
		{
			return new ResponseEntity<User>(userBLL.getUserById(id), HttpStatus.OK);
		}
		catch (BLLException error)
		{
			return new ResponseEntity<Map<String,String>>(error.getErrors(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	//-----------------------------------------

	@PostMapping("/user")
	public ResponseEntity<?> insertUser(@RequestBody User user) throws BLLException 
	{
		
		try 
		{
			userBLL.saveOrUpdate(user);
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		} 
		catch (BLLException error) 
		{
			
			return new ResponseEntity<Map<String,String>>(error.getErrors(), HttpStatus.BAD_REQUEST);
		}
			
		
	}
	
	//-----------------------------------------
	
	/*
	 * Endpoint utilisé pour authentifier un utilisateur au moment du login.
	 * Renvoie une erreur 401 "Unauthorized" si le couple identifiant / mdp est faux
	 * Renvoie un user avec son token si la connexion réussit
	 */
	@PostMapping("/user/login")
	public ResponseEntity<?> get(@RequestBody User user)
	{
		try
		{
			return new ResponseEntity<User>(userBLL.getByLoginAndPassword(user.getEmail(), user.getPassword()), HttpStatus.OK);
		}
		catch(BLLException error)
		{
			return new ResponseEntity<Map<String,String>>(error.getErrors(), HttpStatus.UNAUTHORIZED);
			
		}
		
	}
	
	//-----------------------------------------
	
	/*
	 * Endpoint utilisé pour deconnecter un utilisateur grace à son token
	 */
	@GetMapping("/user/logout")
	public void logout(@RequestHeader("token") String token) 
	{
		userBLL.logout(token, LocalDateTime.now());
	}
	
	//-----------------------------------------

	@PutMapping("/user/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") int id, @RequestBody User user)
	{
		user.setId(id);
		
		try 
		{
			userBLL.saveOrUpdate(user);
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		} 
		catch (BLLException error) 
		{
			
			return new ResponseEntity<Map<String,String>>(error.getErrors(), HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
	//-----------------------------------------
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) 
	{
		userBLL.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//=====================================================
	//restaurantOrder
	
	@GetMapping("/commandes")
	public ResponseEntity<List<RestaurantOrder>> getAll() 
	{
		return new ResponseEntity<>(restaurantOrderBll.getAll(), HttpStatus.OK);
	}
	
		
	@GetMapping("/commandes/{id}")
	public ResponseEntity<RestaurantOrder> getById(@PathVariable("id") int id) 
	{
		try
		{
			return new ResponseEntity<>(restaurantOrderBll.getById(id), HttpStatus.OK);
		}
		catch (BLLException error)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

	@GetMapping("/commandes/resto/{id}")
	public ResponseEntity<List<BillDTO>> getDetailBillWhereStateSoldByOrderByIdTableAndByRestaurantId(@PathVariable("id") int idRestaurant) {
		return new ResponseEntity<>(this.restaurantOrderBll.getDetailBillWhereStateSoldByOrderByIdTableAndByRestaurantId(idRestaurant), HttpStatus.OK);
	}
	
	
	@GetMapping("/commandes/table/{id}")
	public ResponseEntity<List<RestaurantOrder>> getByTableId(@PathVariable("id") int tableId) 
	{
		
		return new ResponseEntity<>(restaurantOrderBll.getByTableId(tableId), HttpStatus.OK);
		
		
	}
	
	
	
	
	@GetMapping("/commandes/bill/{id}")
	public ResponseEntity<Float> getTotalAmountOrderBillById(@PathVariable("id") int id)
	{
		return new ResponseEntity<>(this.restaurantOrderBll.getTotalAmountOrderBillById(id), HttpStatus.OK);
	}
	
	@PostMapping("/commandes")
	public ResponseEntity<RestaurantOrder> insert(@RequestBody RestaurantOrder restaurantOrder) {
		restaurantOrderBll.save(restaurantOrder);
		return new ResponseEntity<>(restaurantOrder, HttpStatus.CREATED);
	}
	
	@PutMapping("/commandes/{id}/modifier-etat")
	public ResponseEntity<Void> updateState(@PathVariable("id") int id, @RequestBody RestaurantOrder restaurantOrder) 
	{
		try
		{
			RestaurantOrder restaurantOrderToUpdate = restaurantOrderBll.getById(id);
			restaurantOrderToUpdate.setState(restaurantOrder.getState());
			restaurantOrderBll.save(restaurantOrderToUpdate);
			return new ResponseEntity<>(HttpStatus.OK);
			
		}
		catch(BLLException error)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping("/commandes/{id}/ajouter-plats")
	public ResponseEntity<Void> updateDishes(@PathVariable("id") int id, @RequestBody RestaurantOrder restaurantOrder) {
		//RestaurantOrder restaurantOrderToUpdate = restaurantOrderBll.getById(id);
		//restaurantOrderBll.updateDishes(id, restaurantOrder);
		restaurantOrderBll.save(restaurantOrder);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/commandes/{id}")
	public ResponseEntity<RestaurantOrder> deleteOrder(@PathVariable("id") int id) 
	{
		try
		{
			RestaurantOrder restaurantOrder = restaurantOrderBll.getById(id);
			restaurantOrderBll.delete(id);
			return new ResponseEntity<>(restaurantOrder, HttpStatus.OK);
			
		}
		catch(BLLException error)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
}
	

