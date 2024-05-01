package pate_d_or.equipe.bll;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
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

import pate_d_or.equipe.dal.ReservationDAO;
import pate_d_or.equipe.entities.Reservation;
import pate_d_or.equipe.entities.RestaurantTable;


@SpringBootTest
@DisplayName("Test for ReservationBLL")
class ReservationBLLTest 
{
	
	//===============================
	//findById
	
	@Nested
	@DisplayName("Testing findById ")
	class findById
	{
		@Mock
		private ReservationDAO dao;
		
		@InjectMocks
		private ReservationBLL reservationBLL;
		
		@Test
		void findById_withExistingId_returnReservation() throws BLLException 
		{
			Reservation reservationMock = new Reservation();
			reservationMock.setId(1);

			Optional<Reservation> reseravationOptional = Optional.of(reservationMock);
			
			Mockito.when(this.dao.findById(1)).thenReturn(reseravationOptional);
			
			Reservation reservation = this.reservationBLL.findById(1);
			
			assertEquals(reservationMock.getId(), reservation.getId());
			
			
		}
		
		//-----------------------------------
		
		@Test
		void findById_withWrongId_throwBLLException() throws BLLException 
		{
			Optional<Reservation> noReservation = Optional.empty();
			
			Mockito.when(this.dao.findById(Mockito.anyInt())).thenReturn(noReservation);
			
			assertThrows(BLLException.class, ()-> this.reservationBLL.findById(-1), "findById with a wrong id should throw BLLException");
			
		}
	
	}
		
	//===============================
	//update
	
	@Nested
	@DisplayName("Testing update ")
	class update
	{
		@Mock
		private ReservationDAO dao;
		
		@InjectMocks
		private ReservationBLL reservationBLL;
		
		private Reservation reservationMock;
		
		//===============================
		
		@BeforeEach
		void initReservationMock()
		{
			RestaurantTable tableMock = new RestaurantTable();
			tableMock.setState(null);
			
			this.reservationMock = new Reservation();
			this.reservationMock.setId(1);
			this.reservationMock.setState("hold");
			this.reservationMock.setTable(tableMock);
			
		}
		
		@AfterEach
		void destroyReservationMock()
		{
			this.reservationMock = null;
		}
		
		//===============================

		@Test
		void update_withCorrectState_saveReservationState() throws BLLException 
		{
			//-----------------------------------------------
			//set up mock
			
			Optional<Reservation> reseravationOptional = Optional.of(this.reservationMock);
			
			Mockito.when(this.dao.findById(1)).thenReturn(reseravationOptional);
			Mockito.when(this.dao.save(this.reservationMock)).thenReturn(this.reservationMock);
			
			//-----------------------------------------------
			//set up test
			
			Reservation updateReservation = new Reservation();
			updateReservation.setId(1);
			updateReservation.setState("gran");
			
			//-----------------------------------------------
			//execute action
			
			this.reservationBLL.update(updateReservation, updateReservation.getId());
			
			//-----------------------------------------------
			//assert

			assertEquals(updateReservation.getState(), this.reservationMock.getState());
			
		}
		
		//-----------------------------------
		
		@Test
		void update_withStateEqualsHere_saveReservationStateAndChangeTableState() throws BLLException 
		{
			//-----------------------------------------------
			//set up mock
			
			Optional<Reservation> reseravationOptional = Optional.of(this.reservationMock);
			
			Mockito.when(this.dao.findById(1)).thenReturn(reseravationOptional);
			Mockito.when(this.dao.save(this.reservationMock)).thenReturn(this.reservationMock);
			
			//-----------------------------------------------
			//set up test
			
			Reservation updateReservation = new Reservation();
			updateReservation.setId(1);
			updateReservation.setState("here");
			
			String rightTableState = "pres";
			
			//-----------------------------------------------
			//execute action
			
			this.reservationBLL.update(updateReservation, updateReservation.getId());
			
			//-----------------------------------------------
			//assert
			
			assertAll("update reservation with 'here' state should update the reservation's etable with 'pres' state",
				    () -> assertEquals(updateReservation.getState(), this.reservationMock.getState()),
				    () -> assertEquals(this.reservationMock.getTable().getState(), rightTableState)
				   
				);
			
		}
		
		//-----------------------------------
		
		@Test
		void update_withRightStateNotEqualsHere_saveReservationStateAndDoNotChangeTableState() throws BLLException 
		{
			//-----------------------------------------------
			//set up mock
			
			Optional<Reservation> reseravationOptional = Optional.of(this.reservationMock);
			
			Mockito.when(this.dao.findById(1)).thenReturn(reseravationOptional);
			Mockito.when(this.dao.save(this.reservationMock)).thenReturn(this.reservationMock);
			
			//-----------------------------------------------
			//set up test
			
			Reservation updateReservation = new Reservation();
			updateReservation.setId(1);
			updateReservation.setState("deni");
			
			String TableState = "pres";
			
			//-----------------------------------------------
			//execute action
			
			this.reservationBLL.update(updateReservation, updateReservation.getId());
			
			//-----------------------------------------------
			//assert
			
			assertAll("update reservation with 'here' state should update the reservation's etable with 'pres' state",
				    () -> assertEquals(updateReservation.getState(), this.reservationMock.getState()),
				    () -> assertNotEquals(this.reservationMock.getTable().getState(), TableState)
				   
				);
			
		}
		
		//-----------------------------------
		
		@Test
		void update_withWrongState_throwBLLException() throws BLLException
		{
			//-----------------------------------------------
			//set up mock
			
			Optional<Reservation> reseravationOptional = Optional.of(this.reservationMock);
			
			Mockito.when(this.dao.findById(1)).thenReturn(reseravationOptional);
			Mockito.when(this.dao.save(this.reservationMock)).thenReturn(this.reservationMock);
			
			//-----------------------------------------------
			//set up test
			
			Reservation updateReservation = new Reservation();
			updateReservation.setId(1);
			updateReservation.setState("dhfdeqhqerhehfhdqhqerhe");
			
			//-----------------------------------------------
			//assert
			
			assertThrows(BLLException.class, ()-> this.reservationBLL.update(updateReservation, updateReservation.getId()), "update with a wrong reservation state should throw BLLException");
			
		}
		
	}
	
	
	

}
