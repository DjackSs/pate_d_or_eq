package pate_d_or.equipe.bll;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import pate_d_or.equipe.dal.DALException;
import pate_d_or.equipe.dal.RestaurantOrderDAO;
import pate_d_or.equipe.entities.Dish;
import pate_d_or.equipe.entities.RestaurantOrder;
import pate_d_or.equipe.entities.RestaurantTable;

@SpringBootTest
@DisplayName("Test for RestaurantOrderBLL")
class RestaurantOrderBLLTest 
{
	//===============================
	//getUserById
	
	@Nested
	@DisplayName("Testing getById ")
	class getById
	{
		@Mock
		private RestaurantOrderDAO dao;
		
		@InjectMocks
		private RestaurantOrderBLL restaurantOrderBLL;
		
		//===============================
		
		@Test
		void getById_withExistingId_returnRestaurantOrderBLL() throws BLLException
		{
			RestaurantOrder orderMock = new RestaurantOrder();
			orderMock.setId(1);
	
			Optional<RestaurantOrder> userOptional = Optional.of(orderMock);
			
			Mockito.when(this.dao.findById(1)).thenReturn(userOptional);
			
			RestaurantOrder order = this.restaurantOrderBLL.getById(1);
			
			assertEquals(orderMock.getId(), order.getId());
			
		}
		
		//-----------------------------------
		
		@Test
		void getById_withWrongId_throwBLLException() throws BLLException 
		{
			Optional<RestaurantOrder> noRestaurantOrder = Optional.empty();
			
			Mockito.when(this.dao.findById(Mockito.anyInt())).thenReturn(noRestaurantOrder);
			
			assertThrows(BLLException.class, ()-> this.restaurantOrderBLL.getById(-1), "getById with a wrong id should throw BLLException");
			
		}
		
	}
	
	//===============================
	//save
	
	@Nested
	@DisplayName("Testing save ")
	class save
	{
		@Mock
		private RestaurantOrderDAO dao;
		
		@InjectMocks
		private RestaurantOrderBLL restaurantOrderBLL;
		
		private static RestaurantOrder dataOrder;
		
		//===============================
		
		@BeforeAll
		static void initDataOrder()
		{
			RestaurantTable dataTable = new RestaurantTable();
			dataTable.setId(1);
			
			Dish dish1 = new Dish();
			dish1.setId(1);
			Dish dish2 = new Dish();
			dish2.setId(2);
			
			List<Dish> dishes = new ArrayList<>();
			dishes.add(dish1);
			dishes.add(dish2);
			
			dataOrder = new RestaurantOrder();
			dataOrder.setId(1);
			dataOrder.setState("take");
			dataOrder.setTable(dataTable);
			dataOrder.setDishes(dishes);
			
			
		}
		
		
		//===============================
		//create RestaurantOrder
				
		@Test
		void save_validRestaurantOrder_saveRestaurantOrder() throws BLLException
		{
			RestaurantTable validTable = new RestaurantTable();
			validTable.setId(2);
			
			RestaurantOrder validRestaurantOrder = new RestaurantOrder();
			validRestaurantOrder.setState(null);
			validRestaurantOrder.setTable(validTable);
			
			Mockito.when(this.dao.findByTableId(validRestaurantOrder.getTable().getId())).thenReturn(null);
			Mockito.when(this.dao.save(validRestaurantOrder)).thenReturn(validRestaurantOrder);
			
			this.restaurantOrderBLL.save(validRestaurantOrder);
			
			assertNotNull(validRestaurantOrder);
		}
		
		//-----------------------------------
		
		@Test
		void save_RestaurantOrderWithWrongState_throwException() throws BLLException
		{
			RestaurantTable validTable = new RestaurantTable();
			validTable.setId(2);
			
			RestaurantOrder invalidRestaurantOrder = new RestaurantOrder();
			invalidRestaurantOrder.setState("invalidState");
			invalidRestaurantOrder.setTable(validTable);
			
			Mockito.when(this.dao.findByTableId(invalidRestaurantOrder.getTable().getId())).thenReturn(null);
			
			assertThrows(BLLException.class, ()-> this.restaurantOrderBLL.save(invalidRestaurantOrder), "save restaurantOrder with invalid state should throw BLLException");
			
		}
		
		//-----------------------------------
		
		@Test
		void save_RestaurantOrderWithoutTable_throwException() throws BLLException
		{
			RestaurantOrder invalidRestaurantOrder = new RestaurantOrder();
			invalidRestaurantOrder.setState(null);
			
			assertThrows(BLLException.class, ()-> this.restaurantOrderBLL.save(invalidRestaurantOrder), "save restaurantOrder without a table should throw BLLException");
		}
		
		//-----------------------------------
		
		@Test
		void save_RestaurantOrderWithTableThatHasAlreadyAnOrder_throwException() throws BLLException
		{
			RestaurantTable invalidTable = new RestaurantTable();
			invalidTable.setId(1);
			
			RestaurantOrder invalidRestaurantOrder = new RestaurantOrder();
			invalidRestaurantOrder.setState(null);
			invalidRestaurantOrder.setTable(invalidTable);
			
			Mockito.when(this.dao.findByTableId(invalidRestaurantOrder.getTable().getId())).thenReturn(dataOrder);
			
			assertThrows(BLLException.class, ()-> this.restaurantOrderBLL.save(invalidRestaurantOrder), "save restaurantOrder with a table that has already a RestaurantOrder should throw BLLException");
		}
		
		//===============================
		//update RestaurantOrder
		
		@Test
		void save_withSameRestaurantOrder_returnDataOrder() throws DALException, BLLException
		{
			//-----------------------------------------------
			//set up mock
			
			RestaurantOrder sameOrder = dataOrder;
			Optional<RestaurantOrder> optionalDataOrder = Optional.of(dataOrder);
			
			//-----------------------------------------------
			//set up test
			
			Mockito.when(this.dao.findById(sameOrder.getId())).thenReturn(optionalDataOrder);
			Mockito.when(this.dao.save(sameOrder)).thenReturn(sameOrder);
			
			//-----------------------------------------------
			//execute action
			
			this.restaurantOrderBLL.save(sameOrder);
			
			//-----------------------------------------------
			//assert
			
			assertAll("save RestaurantOrder with same informations should return same RestaurantOrder",
				    () -> assertEquals(sameOrder.getState(), dataOrder.getState()),
				    () -> assertEquals(sameOrder.getTable().getId(), dataOrder.getTable().getId()),
				    () -> assertEquals(sameOrder.getDishes().size(), dataOrder.getDishes().size())
				    
				);
		}
		
		//-----------------------------------
		
		@Test
		void save_withEmptyValuesAndExistingId_returnDataDataOrderWithNullState() throws DALException, BLLException
		{
			//-----------------------------------------------
			//set up mock
			
			Optional<RestaurantOrder> optionalDataOrder = Optional.of(dataOrder);
			
			//-----------------------------------------------
			//set up test
			
			RestaurantOrder emptyOrder = new RestaurantOrder();
			emptyOrder.setId(1);
			
			
			Mockito.when(this.dao.findById(emptyOrder.getId())).thenReturn(optionalDataOrder);
			Mockito.when(this.dao.save(emptyOrder)).thenReturn(emptyOrder);
			
			//-----------------------------------------------
			//execute action
			
			this.restaurantOrderBLL.save(emptyOrder);
			
			//-----------------------------------------------
			//assert
			
			assertAll("save RestaurantOrder with empty informations should return database RestaurantOrder and change the state to null",
					() -> assertNotEquals(emptyOrder.getState(), dataOrder.getState()),
				    () -> assertEquals(emptyOrder.getTable().getId(), dataOrder.getTable().getId()),
				    () -> assertEquals(emptyOrder.getDishes().size(), dataOrder.getDishes().size())
				);
		}
		
		//-----------------------------------
		
	}



}
