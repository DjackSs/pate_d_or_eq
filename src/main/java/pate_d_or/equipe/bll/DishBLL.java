package pate_d_or.equipe.bll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pate_d_or.equipe.dal.DishDAO;
import pate_d_or.equipe.entities.Dish;

@Service
public class DishBLL {
	@Autowired private DishDAO dishDao;
	
	public Dish getById(int id) {
		return dishDao.findById(id).get();
	}

}
