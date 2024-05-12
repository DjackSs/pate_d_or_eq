package pate_d_or.equipe.bll;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import pate_d_or.equipe.dal.UserDAO;
import pate_d_or.equipe.entities.User;


@SpringBootTest
@DisplayName("Test for UserBLL")
class UserBLLTest 
{
	//===============================
	//getUserById
		
	@Nested
	@DisplayName("Testing findById ")
	class getUserById
	{
		@Mock
		private UserDAO dao;
		
		@InjectMocks
		private UserBLL userBLL;
		
		//===============================
		
		@Test
		void getUserById_withExistingId_returnUser() throws BLLException
		{
			User UserMock = new User();
			UserMock.setId(1);
	
			Optional<User> userOptional = Optional.of(UserMock);
			
			Mockito.when(this.dao.findById(1)).thenReturn(userOptional);
			
			User user = this.userBLL.getUserById(1);
			
			assertEquals(UserMock.getId(), user.getId());
			
		}
		
		//-----------------------------------
		
		@Test
		void getUserById_withWrongId_throwBLLException() throws BLLException 
		{
			Optional<User> noUser = Optional.empty();
			
			Mockito.when(this.dao.findById(Mockito.anyInt())).thenReturn(noUser);
			
			assertThrows(BLLException.class, ()-> this.userBLL.getUserById(-1), "getUserById with a wrong id should throw BLLException");
			
		}
		
	}
	
	//===============================
	//getByLoginAndPassword
	
	@Nested
	@DisplayName("Testing getByLoginAndPassword ")
	class getByLoginAndPassword
	{
		
		@Mock
		private UserDAO dao;
		
		@InjectMocks
		private UserBLL userBLL;
		
		
		//===============================
		
		@Test
		void getByLoginAndPassword_withValidEmailAndPassword_returnUser() throws BLLException
		{
			//-----------------------------------------------
			//set up mock
			
			User userMock = new User();
			userMock.setId(1);
			userMock.setEmail("email@right.com");
			userMock.setPassword(BCrypt.hashpw("R1ght!", BCrypt.gensalt()));
			
			//-----------------------------------------------
			//set up test
			
			String email = "email@right.com";
			String password = "R1ght!";
			
			List <User> users = new ArrayList<>();
			users.add(userMock);
			
			Mockito.when(this.dao.findByEmail(email)).thenReturn(users);
			
			User userResult = null;
			
			//-----------------------------------------------
			//execute action
			
			userResult = this.userBLL.getByLoginAndPassword(email, password);
			
			//-----------------------------------------------
			//assert
			
			assertNotNull(userResult);
			
				
		}
		
		//-----------------------------------
		
		@Test
		void getByLoginAndPassword_withEmailNullAndPasswordNull_throwBLLException()
		{
			assertThrows(BLLException.class, ()-> this.userBLL.getByLoginAndPassword(null, null), "getByLoginAndPassword with email = null and password = null should throw BLLException");
			
		}
		
		//-----------------------------------
		
		@Test
		void getByLoginAndPassword_withEmailEmptyAndPasswordEmpty_throwBLLException()
		{
			String emptyEmail = "                                 ";
			String emptyPassword = "";
			
			assertThrows(BLLException.class, ()-> this.userBLL.getByLoginAndPassword(emptyEmail, emptyPassword), "getByLoginAndPassword with empty email and empty password should throw BLLException");
			
		}
		
		//-----------------------------------
		
		@Test
		void getByLoginAndPassword_withWrongEmailAndWrongPassword_trowBLLException()
		{
			String wrongEmail = "email@wrong.com";
			String wrongPassword = "wrong";
			
			List <User> users = new ArrayList<>();
			
			Mockito.when(this.dao.findByEmail(wrongEmail)).thenReturn(users);
			
			assertThrows(BLLException.class, ()-> this.userBLL.getByLoginAndPassword(wrongEmail, wrongPassword), "getByLoginAndPassword with wrong email and wrong password should throw BLLException");
			
		}
		
		//-----------------------------------
		
		@Test
		void getByLoginAndPassword_withRightEmailAndWrongPassword_trowBLLException()
		{
			//-----------------------------------------------
			//set up mock
			
			User userMock = new User();
			userMock.setId(1);
			userMock.setEmail("email@right.com");
			userMock.setPassword(BCrypt.hashpw("R1ght!", BCrypt.gensalt()));
			
			//-----------------------------------------------
			//set up test
			
			String Email = "email@right.com";
			String wrongPassword = "wrong";
			
			List <User> users = new ArrayList<>();
			users.add(userMock);
			
			Mockito.when(this.dao.findByEmail(Email)).thenReturn(users);
			
			//-----------------------------------------------
			//assert
			
			assertThrows(BLLException.class, ()-> this.userBLL.getByLoginAndPassword(Email, wrongPassword), "getByLoginAndPassword with right email and wrong password should throw BLLException");
			
		}
		
		
	}
	
	//===============================
	//getByToken
	
	@Nested
	@DisplayName("Testing getByToken ")
	class getByToken
	{
		@Mock
		private UserDAO dao;
		
		@InjectMocks
		private UserBLL userBLL;
		
		//===============================
		
		@Test
		void getByToken_withRightToken_returnUser() throws BLLException
		{
			//-----------------------------------------------
			//set up mock
			
			User userMock = new User();
			userMock.setToken("token");
			
			//-----------------------------------------------
			//set up test
			
			String rightToken = "token";
			LocalDateTime time = LocalDateTime.now();
			
			when(this.dao.findByTokenAndExpirationTimeAfter(rightToken, time)).thenReturn(userMock);
			when(this.dao.save(userMock)).thenReturn(userMock);
			
			User userResult = null;
			
			//-----------------------------------------------
			//execute action
			
			userResult = this.userBLL.getByToken(rightToken, time);
			
			//-----------------------------------------------
			//assert
			
			assertNotNull(userResult);
			
			
		}
		
		@Test
		void getByToken_withExpiredToken_throwBLLException() throws BLLException
		{
			//-----------------------------------------------
			//set up mock
			
			User userMock = new User();
			userMock.setToken("token");
			
			//-----------------------------------------------
			//set up test
			
			String rightToken = "token";
			LocalDateTime expiredTime = LocalDateTime.of(1900, 01, 01, 0, 0);
			
			when(this.dao.findByTokenAndExpirationTimeAfter(rightToken, LocalDateTime.now())).thenReturn(userMock);
			when(this.dao.save(userMock)).thenReturn(userMock);
			
			//-----------------------------------------------
			//assert
			
			assertThrows(BLLException.class, ()-> this.userBLL.getByToken(rightToken, expiredTime), "getByToken with expired time should throw BLLException");
			
			
			
		}
		
		@Test
		void getByToken_withoutToken_throwBLLException() throws BLLException
		{
			//-----------------------------------------------
			//set up mock
			
			User userMock = new User();
			userMock.setToken("token");
			
			//-----------------------------------------------
			//set up test
			
			when(this.dao.findByTokenAndExpirationTimeAfter(null, LocalDateTime.now())).thenReturn(null);
			when(this.dao.save(userMock)).thenReturn(userMock);
			
			//-----------------------------------------------
			//assert
			
			assertThrows(BLLException.class, ()-> this.userBLL.getByToken(null, LocalDateTime.now()), "getByToken without token should throw BLLException");
			
			
			
		}
		
		
	}
	
	//===============================
	//logout
	
	@Nested
	@DisplayName("Testing logout ")
	class logout
	{
		@Mock
		private UserDAO dao;
		
		@InjectMocks
		private UserBLL userBLL;
		
		//===============================
		
		@Test
		void logout_withRightToken_setTokenAndexpirationTimeToNull() 
		{
			//-----------------------------------------------
			//set up mock
			
			User userMock = new User();
			userMock.setToken("token");
			userMock.setExpirationTime(LocalDateTime.now());
			
			//-----------------------------------------------
			//set up test
			
			String rightToken = "token";
			LocalDateTime time = LocalDateTime.now();
			
			when(this.dao.findByTokenAndExpirationTimeAfter(rightToken, LocalDateTime.now())).thenReturn(userMock);
			when(this.dao.save(userMock)).thenReturn(userMock);
			
			//-----------------------------------------------
			//execute action
			
			this.userBLL.logout(rightToken, time);
			
			//-----------------------------------------------
			//assert
			
			assertAll("logout with right token should set token and expiration time to null",
				    () -> assertNull(userMock.getToken()),
				    () -> assertNull(userMock.getExpirationTime())
				   
				);
			
		}
		
		
	}
	
	//===============================
	//saveOrUpdate
	
	@Nested
	@DisplayName("Testing saveOrUpdate ")
	class saveOrUpdate
	{
		@Mock
		private UserDAO dao;
		
		@InjectMocks
		private UserBLL userBLL;
		
	}



}
