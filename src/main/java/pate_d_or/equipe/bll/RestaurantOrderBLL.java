package pate_d_or.equipe.bll;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pate_d_or.equipe.dal.RestaurantOrderDAO;
import pate_d_or.equipe.dto.BillDTO;
import pate_d_or.equipe.entities.Dish;
import pate_d_or.equipe.entities.RestaurantOrder;

@Service
public class RestaurantOrderBLL {
	@Autowired private RestaurantOrderDAO restaurantOrderDao;
	
	public List<RestaurantOrder> getAll() {
		return (List<RestaurantOrder>) restaurantOrderDao.findAll();
	}
	
	public RestaurantOrder getById(int id) throws BLLException 
	{
		BLLException bll = new BLLException();
		
		if(restaurantOrderDao.findById(id).isEmpty())
		{
			bll.addError("order", "Commande inconue");
			throw bll;
			
		}
		
		return restaurantOrderDao.findById(id).get();
	}
	
	public List<RestaurantOrder> getByTableId(int tableId)
	{
		return this.restaurantOrderDao.findByTableId(tableId);
	}

	public List<BillDTO> getDetailBillWhereStateSoldAndOrderByIdTable() {
		List<Object[]> result = this.restaurantOrderDao.getDetailBillWhereStateSoldAndOrderByIdTable();
		List<BillDTO> bills = new ArrayList<>();
		for (Object[] current : result) {
			BillDTO bill = new BillDTO();
			bill.setTableNumber((int) current[0]);
			bill.setDishName((String) current[1]);
			bill.setDishPrice((BigDecimal) current[2]);
			bills.add(bill);
		}
		return bills;
	}

	public Float getTotalAmountOrderBillById(int id)
	{
		return this.restaurantOrderDao.getTotalAmountOrderBillById(id);
	}
	
	public void save(RestaurantOrder restaurantOrder) {
		restaurantOrderDao.save(restaurantOrder);
	}
	
	public void updateDishes(int id,RestaurantOrder restaurantOrder) {
		RestaurantOrder restaurantOrderToUpdate = restaurantOrderDao.findById(id).get();
		List<Dish> dishesToAdd = restaurantOrderToUpdate.getDishes();
		for (Dish current : restaurantOrder.getDishes()) {
			dishesToAdd.add(current);
		}
		restaurantOrderToUpdate.setDishes(dishesToAdd);
		restaurantOrderDao.save(restaurantOrderToUpdate);
	}
	
	public void delete(int id) { restaurantOrderDao.deleteById(id); }

}
