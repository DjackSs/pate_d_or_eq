package pate_d_or.equipe.bll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pate_d_or.equipe.dal.RestaurantOrderDAO;
import pate_d_or.equipe.entities.RestaurantOrder;

@Service
public class RestaurantOrderBLL {
	@Autowired private RestaurantOrderDAO restaurantOrderDao;
	
	public List<RestaurantOrder> getAll() {
		return (List<RestaurantOrder>) restaurantOrderDao.findAll();
	}
}
