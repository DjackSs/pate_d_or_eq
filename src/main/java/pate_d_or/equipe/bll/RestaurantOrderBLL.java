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
	
	public RestaurantOrder getById(int id) {
		return restaurantOrderDao.findById(id).get();
	}

	public Float getOrderBillById(int id)
	{
		return this.restaurantOrderDao.getOrderBillById(id);
	}
	
	public void save(RestaurantOrder restaurantOrder) {
		restaurantOrderDao.save(restaurantOrder);
	}
	
	public void delete(int id) { restaurantOrderDao.deleteById(id); }

}
