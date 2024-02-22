package pate_d_or.equipe.controller;

import java.util.List;

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

import pate_d_or.equipe.bll.admin.AdminCardBLL;
import pate_d_or.equipe.bll.admin.AdminRestaurantBLL;
import pate_d_or.equipe.entities.admin.AdminCard;
import pate_d_or.equipe.entities.admin.AdminRestaurant;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminRest 
{
	@Autowired
	private AdminRestaurantBLL adminRestaurantBLL;
	
	@Autowired
	private AdminCardBLL adminCardBLL;
	
	//====================================================
	//restaurant
	
	@GetMapping("/resto")
	public ResponseEntity<List<AdminRestaurant>> findAllRestaurant()
	{
		return new ResponseEntity<>(this.adminRestaurantBLL.findAll(), HttpStatus.OK);
	}
	
	//----------------------------------------------------
	
	@GetMapping("/resto/{id}")
	public ResponseEntity<AdminRestaurant> findRestaurant(@PathVariable("id") int id)
	{
		return new ResponseEntity<>(this.adminRestaurantBLL.findById(id), HttpStatus.OK);
	}
	
	//----------------------------------------------------
	
	@PostMapping("/resto")
	public ResponseEntity<AdminRestaurant> insertRestaurant(@RequestBody AdminRestaurant newRestaurant)
	{
		this.adminRestaurantBLL.save(newRestaurant);
		return new ResponseEntity<>(newRestaurant, HttpStatus.CREATED);
	}
	
	//----------------------------------------------------
	
	@PutMapping("/resto/{id}")
	public ResponseEntity<AdminRestaurant> insertRestaurant(@PathVariable("id") int id, @RequestBody AdminRestaurant updateRestaurant)
	{
		updateRestaurant.setId(this.adminRestaurantBLL.findById(id).getId());
		this.adminRestaurantBLL.save(updateRestaurant);
		
		return new ResponseEntity<>(updateRestaurant, HttpStatus.OK);
	}
	
	//----------------------------------------------------
	
	@DeleteMapping("/resto/{id}")
	public ResponseEntity<Void> deleteRestaurant(@PathVariable("id") int id)
	{
		this.adminRestaurantBLL.delete(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//====================================================
	//carte
	
	@GetMapping("/card")
	public ResponseEntity<List<AdminCard>> findAllCard()
	{
		return new ResponseEntity<>(this.adminCardBLL.findAll(), HttpStatus.OK);
	}
	
	//----------------------------------------------------
	
	@GetMapping("/card/{id}")
	public ResponseEntity<AdminCard> findCard(@PathVariable("id") int id)
	{
		return new ResponseEntity<>(this.adminCardBLL.findById(id), HttpStatus.OK);
	}
	
	//----------------------------------------------------
	
	@PostMapping("/card")
	public ResponseEntity<AdminCard> insertCard(@RequestBody AdminCard newCard)
	{
		this.adminCardBLL.save(newCard);
		return new ResponseEntity<>(newCard, HttpStatus.CREATED);
	}
	
	//----------------------------------------------------
	
	@PutMapping("/card/{id}")
	public ResponseEntity<AdminCard> insertRestaurant(@PathVariable("id") int id, @RequestBody AdminCard updateCard)
	{
		updateCard.setId(this.adminCardBLL.findById(id).getId());
		this.adminCardBLL.save(updateCard);
		
		return new ResponseEntity<>(updateCard, HttpStatus.OK);
	}
	
	//----------------------------------------------------
	
	@DeleteMapping("/card/{id}")
	public ResponseEntity<Void> deleteCard(@PathVariable("id") int id)
	{
		this.adminRestaurantBLL.delete(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	

}
