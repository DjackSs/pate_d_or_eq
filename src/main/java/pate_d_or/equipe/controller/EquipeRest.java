package pate_d_or.equipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pate_d_or.equipe.bll.RestaurantOrderBLL;
import pate_d_or.equipe.entities.RestaurantOrder;

@RestController
@CrossOrigin
@RequestMapping("/pate_d_or")
public class EquipeRest {
	@Autowired private RestaurantOrderBLL restaurantOrderBll;
	
	@GetMapping("/commandes")
	public List<RestaurantOrder> getAll() {
		return restaurantOrderBll.getAll();
	}
	
}
