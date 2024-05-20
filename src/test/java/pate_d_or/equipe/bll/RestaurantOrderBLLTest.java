package pate_d_or.equipe.bll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import pate_d_or.equipe.dal.RestaurantOrderDAO;
import pate_d_or.equipe.entities.RestaurantOrder;

@SpringBootTest
@DisplayName("Test for RestaurantOrderBLL")
class RestaurantOrderBLLTest 
{
	//===============================
	//getUserById
	
	@Nested
	@DisplayName("Testing findById ")
	class getUserById
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



}
