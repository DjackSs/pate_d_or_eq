package pate_d_or.equipe.bll;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pate_d_or.equipe.dal.RestaurantOrderDAO;
import pate_d_or.equipe.dto.BillDTO;
import pate_d_or.equipe.entities.RestaurantOrder;

@Service
public class RestaurantOrderBLL 
{
	@Autowired private RestaurantOrderDAO restaurantOrderDao;
	
	private static final List<String> ORDER_STATE = new ArrayList<>(Arrays.asList("take", "read", "serv", "sold", null));
	
	//====================================================================
	
	public List<RestaurantOrder> getAll() {
		return (List<RestaurantOrder>) restaurantOrderDao.findAll();
	}
	
	//--------------------------------------------------------------------
	
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
	
	//--------------------------------------------------------------------
	
	public RestaurantOrder getByTableId(int tableId)
	{
		return this.restaurantOrderDao.findByTableId(tableId);
	}
	
	//--------------------------------------------------------------------
	

	public List<BillDTO> getDetailBillWhereStateSoldByOrderByIdTableAndByRestaurantId(int idRestaurant) 
	{
		List<Object[]> result = this.restaurantOrderDao.getDetailBillWhereStateSoldByOrderByIdTableAndByRestaurantId(idRestaurant);
		
		List<BillDTO> bills = new ArrayList<>();
		
		for (Object[] current : result) 
		{
			BillDTO bill = new BillDTO();
			bill.setTableNumber((int) current[0]);
			bill.setDishName((String) current[1]);
			bill.setDishPrice((BigDecimal) current[2]);
			bills.add(bill);
		}
		
		return bills;
	}
	
	//--------------------------------------------------------------------

	public Float getTotalAmountOrderBillById(int id)
	{
		return this.restaurantOrderDao.getTotalAmountOrderBillById(id);
	}
	
	//--------------------------------------------------------------------
	
	public void save(RestaurantOrder restaurantOrder) throws BLLException 
	{
		BLLException bll = new BLLException();
		
		RestaurantOrder oldOrder = null;
		
		if(restaurantOrder.getId() != 0)
		{
			try
			{
				oldOrder = this.getById(restaurantOrder.getId());
				
			}
			catch(BLLException error)
			{
				bll.addError("order", "commande inconnu");
				throw bll;
			}
			
		}
		
		//state
		if(!ORDER_STATE.contains(restaurantOrder.getState())) 
		{
			bll.addError("state", "Etat de la commande invalide");
		}
		
		
		//table
		if(restaurantOrder.getTable() == null && oldOrder == null)
		{
			bll.addError("table", "Table requise pour ouvrir une commande");
		}
		else if(oldOrder == null)
		{
			if(this.getByTableId(restaurantOrder.getTable().getId()) != null)
			{
				bll.addError("table", "Une seule commande par table");
			}
			
		}
		else
		{
			restaurantOrder.setTable(oldOrder.getTable());
		}
		
		//dishes
		if(oldOrder != null && restaurantOrder.getDishes() == null)
		{
			restaurantOrder.setDishes(oldOrder.getDishes());
		}
			
		
		if(bll.getErrors().size() != 0) {
			throw bll;
		}
		
		
		restaurantOrderDao.save(restaurantOrder);
		
		
	}
	
	
	//--------------------------------------------------------------------
	
	public void delete(int id) { restaurantOrderDao.deleteById(id); }

}
