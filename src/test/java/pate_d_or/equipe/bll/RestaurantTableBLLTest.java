package pate_d_or.equipe.bll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import pate_d_or.equipe.dal.RestaurantTableDAO;
import pate_d_or.equipe.entities.RestaurantTable;


@SpringBootTest
@DisplayName("Test for RestaurantTableBLLTest")
class RestaurantTableBLLTest 
{

	//===============================
	//findById
		
	@Nested
	@DisplayName("Testing findById ")
	class findById
	{
		@Mock
		private RestaurantTableDAO dao;
		
		@InjectMocks
		private RestaurantTableBLL restaurantTableBLL;
		
		@Test
		void findById_withExistingId_returnRestaurantTable() throws BLLException 
		{
			RestaurantTable tableMock = new RestaurantTable();
			tableMock.setId(1);
	
			Optional<RestaurantTable> tableOptional = Optional.of(tableMock);
			
			Mockito.when(this.dao.findById(1)).thenReturn(tableOptional);
			
			RestaurantTable table = this.restaurantTableBLL.findById(1);
			
			assertEquals(tableMock.getId(), table.getId());
			
			
		}
		
		//-----------------------------------
		
		@Test
		void findById_withWrongId_throwBLLException() throws BLLException 
		{
			Optional<RestaurantTable> noTable = Optional.empty();
			
			Mockito.when(this.dao.findById(Mockito.anyInt())).thenReturn(noTable);
			
			assertThrows(BLLException.class, ()-> this.restaurantTableBLL.findById(-1), "findById with a wrong id should throw BLLException");
			
		}
	
	}
	
	//===============================
	//update
		
	@Nested
	@DisplayName("Testing update ")
	class update
	{
		@Mock
		private RestaurantTableDAO dao;
		
		@InjectMocks
		private RestaurantTableBLL restaurantTableBLL;
		
		private RestaurantTable tableMock;
		
		//===============================
		
		@BeforeEach
		void initTableMock()
		{
			this.tableMock = new RestaurantTable();
			this.tableMock.setId(1);
			
		}
		
		@AfterEach
		void destroyReservationMock()
		{
			this.tableMock = null;
		}
		
		//===============================

		@Test
		void update_withCorrectState_saveTableState() throws BLLException 
		{
			//-----------------------------------------------
			//set up mock
			
			Optional<RestaurantTable> tableOptional = Optional.of(this.tableMock);
			
			Mockito.when(this.dao.findById(1)).thenReturn(tableOptional);
			Mockito.when(this.dao.save(this.tableMock)).thenReturn(this.tableMock);
			
			//-----------------------------------------------
			//set up test
			
			RestaurantTable updateTable = new RestaurantTable();
			updateTable.setId(1);
			updateTable.setState("pres");
			
			//-----------------------------------------------
			//execute action
			
			this.restaurantTableBLL.update(updateTable, updateTable.getId());
			
			//-----------------------------------------------
			//assert

			assertEquals(updateTable.getState(), this.tableMock.getState());
			
		}
		
		//-----------------------------------
		
		@Test
		void update_withStateEqualsNull_saveTableStateAsNull() throws BLLException 
		{
			//-----------------------------------------------
			//set up mock
			
			Optional<RestaurantTable> tableOptional = Optional.of(this.tableMock);
			
			Mockito.when(this.dao.findById(1)).thenReturn(tableOptional);
			Mockito.when(this.dao.save(this.tableMock)).thenReturn(this.tableMock);
			
			//-----------------------------------------------
			//set up test
			
			RestaurantTable updateTable = new RestaurantTable();
			updateTable.setId(1);
			updateTable.setState(null);
			
			
			//-----------------------------------------------
			//execute action
			
			this.restaurantTableBLL.update(updateTable, updateTable.getId());
			
			//-----------------------------------------------
			//assert
			
			assertEquals(updateTable.getState(), this.tableMock.getState());
			
		}
		
		//-----------------------------------
		
		@Test
		void update_withWrongState_throwBLLException() throws BLLException
		{
			//-----------------------------------------------
			//set up mock
			
			Optional<RestaurantTable> tableOptional = Optional.of(this.tableMock);
			
			Mockito.when(this.dao.findById(1)).thenReturn(tableOptional);
			Mockito.when(this.dao.save(this.tableMock)).thenReturn(this.tableMock);
			
			//-----------------------------------------------
			//set up test
			
			RestaurantTable updateTable = new RestaurantTable();
			updateTable.setId(1);
			updateTable.setState("regzeglkn,zgkjzrngkizjgnz");
			
			//-----------------------------------------------
			//assert
			
			assertThrows(BLLException.class, ()-> this.restaurantTableBLL.update(updateTable, updateTable.getId()), "update with a wrong table state should throw BLLException");
			
		}
		
	}

}
