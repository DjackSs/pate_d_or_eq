package pate_d_or.equipe.bll;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
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
import org.springframework.security.crypto.bcrypt.BCrypt;

import pate_d_or.equipe.dal.DALException;
import pate_d_or.equipe.dal.UserDAO;
import pate_d_or.equipe.entities.Message;
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
			
			Mockito.when(this.dao.findByTokenAndExpirationTimeAfter(rightToken, time)).thenReturn(userMock);
			Mockito.when(this.dao.save(userMock)).thenReturn(userMock);
			
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
			
			Mockito.when(this.dao.findByTokenAndExpirationTimeAfter(rightToken, LocalDateTime.now())).thenReturn(userMock);
			Mockito.when(this.dao.save(userMock)).thenReturn(userMock);
			
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
			
			Mockito.when(this.dao.findByTokenAndExpirationTimeAfter(null, LocalDateTime.now())).thenReturn(null);
			Mockito.when(this.dao.save(userMock)).thenReturn(userMock);
			
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
			
			Mockito.when(this.dao.findByTokenAndExpirationTimeAfter(rightToken, LocalDateTime.now())).thenReturn(userMock);
			Mockito.when(this.dao.save(userMock)).thenReturn(userMock);
			
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
		
		private static User dataBaseUser;
		
		//===============================
		
		@BeforeAll
		static void initDataUser()
		{
			List<Message> messages = new ArrayList<>();
			Message messageMock = new Message();
			messageMock.setId(1);
			
			messages.add(messageMock);
			
			dataBaseUser = new User();
			dataBaseUser.setId(1);
			dataBaseUser.setName("userName");
			dataBaseUser.setLastname("userLastName");	
			dataBaseUser.setEmail("user@mail.com");
			dataBaseUser.setPassword("ValidPassord1!");
			dataBaseUser.setRole("staf");
			dataBaseUser.setMessages(messages);
			
		}
		
		//===============================
		//create user
		
		@Test
		void saveOrUpdate_validUser_saveUser() throws BLLException
		{
			User validUser = new User();
			validUser.setName("userName");
			validUser.setLastname("userLastName");	
			validUser.setEmail("user@mail.com");
			validUser.setPassword("ValidPassord1!");
			validUser.setRole("staf");
			
			Mockito.when(this.dao.save(validUser)).thenReturn(validUser);
			
			this.userBLL.saveOrUpdate(validUser);
			
			assertNotNull(validUser);
		}
		
		//-----------------------------------
		
		@Test
		void saveOrUpdate_withSameCredentials_createTwoUsersWithDifferentPassword() throws BLLException
		{
			//-----------------------------------------------
			//set up mock
			
			User user1 = new User();
			user1.setName("existingUserName");
			user1.setLastname("existingUserLastName");	
			user1.setEmail("existingUser@mail.com");
			user1.setPassword("ValidPassord1!");
			user1.setRole("staf");
			
			User user2 = new User();
			user2.setName("existingUserName");
			user2.setLastname("existingUserLastName");	
			user2.setEmail("existingUser@mail.com");
			user2.setPassword("ValidPassord1!");
			user2.setRole("staf");
			
			//-----------------------------------------------
			//set up test
			
			Mockito.when(this.dao.save(user1)).thenReturn(user1);
			Mockito.when(this.dao.save(user2)).thenReturn(user2);
			
			//-----------------------------------------------
			//execute action
			
			this.userBLL.saveOrUpdate(user1);
			this.userBLL.saveOrUpdate(user2);
			
			//-----------------------------------------------
			//assert
			
			assertAll("Two Users with same credentials should have diferent encrypted password",
				    () -> assertEquals(user1.getName(), user2.getName()),
				    () -> assertEquals(user1.getLastname(), user2.getLastname()),
				    () -> assertEquals(user1.getEmail(), user2.getEmail()),
				    () -> assertEquals(user1.getRole(), user2.getRole()),
				    () -> assertNotEquals(user1.getPassword(), user2.getPassword())
				);
			
		}
		
		//-----------------------------------
		
		@Test
		void saveOrUpdate_withEmailThatDoNotMatchRegex_throwBLLException()
		{
			User invalidUser = new User();
			invalidUser.setName("userName");
			invalidUser.setLastname("userLastName");	
			invalidUser.setEmail("invalidEmail");
			invalidUser.setPassword("ValidPassord1!");
			invalidUser.setRole("staf");
			
			assertThrows(BLLException.class, ()-> this.userBLL.saveOrUpdate(invalidUser), "insert with invalid User email should throw BLLException");
			
		}
		
		//-----------------------------------
		
		@Test
		void saveOrUpdate_withPasswordThatDoNotMatchRegex_throwBLLException()
		{
			User invalidUser = new User();
			invalidUser.setName("userName");
			invalidUser.setLastname("userLastName");	
			invalidUser.setEmail("user@mail.com");
			invalidUser.setPassword("invalidPassword");
			invalidUser.setRole("staf");
			
			assertThrows(BLLException.class, ()-> this.userBLL.saveOrUpdate(invalidUser), "insert with invalid User password should throw BLLException");
			
		}
		
		//===============================
		//update user
		
		@Test
		void saveOrUpdate_withSameUser_returnDataBaseUser() throws DALException, BLLException
		{
			User sameUser = dataBaseUser;
			
			Optional<User> optionalDatabaseUser = Optional.of(dataBaseUser);
			
			Mockito.when(this.dao.findById(sameUser.getId())).thenReturn(optionalDatabaseUser);
			Mockito.when(this.dao.save(sameUser)).thenReturn(sameUser);
			
			this.userBLL.saveOrUpdate(sameUser);
			
			assertAll("update user with same credentials should return same user",
				    () -> assertEquals(sameUser.getName(), dataBaseUser.getName()),
				    () -> assertEquals(sameUser.getLastname(), dataBaseUser.getLastname()),
				    () -> assertEquals(sameUser.getEmail(), dataBaseUser.getEmail()),
				    () -> assertEquals(sameUser.getRole(), dataBaseUser.getRole()),
				    () -> assertEquals(sameUser.getPassword(), dataBaseUser.getPassword())
				);
		}
		
		//-----------------------------------
		
		@Test
		void saveOrUpdate_withEmptyValuesAndExistingId_returnDataBaseUser() throws DALException, BLLException
		{
			
			//-----------------------------------------------
			//set up mock
			
			Optional<User> optionalDatabaseUser = Optional.of(dataBaseUser);
			
			//-----------------------------------------------
			//set up test
			
			User emptyUser = new User();
			emptyUser.setId(1);
			
			
			Mockito.when(this.dao.findById(emptyUser.getId())).thenReturn(optionalDatabaseUser);
			Mockito.when(this.dao.save(emptyUser)).thenReturn(emptyUser);
			
			//-----------------------------------------------
			//execute action
			
			this.userBLL.saveOrUpdate(emptyUser);
			
			//-----------------------------------------------
			//assert
			
			assertAll("update user with empty credentials should return database user",
				    () -> assertEquals(emptyUser.getName(), dataBaseUser.getName()),
				    () -> assertEquals(emptyUser.getLastname(), dataBaseUser.getLastname()),
				    () -> assertEquals(emptyUser.getEmail(), dataBaseUser.getEmail()),
				    () -> assertEquals(emptyUser.getRole(), dataBaseUser.getRole()),
				    () -> assertEquals(emptyUser.getPassword(), dataBaseUser.getPassword())
				);
		}
		
		//-----------------------------------
		
		@Test
		void saveOrUpdate_withDifferentValidDatas_returnUpdatedUser() throws DALException, BLLException
		{
			
			//-----------------------------------------------
			//set up mock
			
			Optional<User> optionalDatabaseUser = Optional.of(dataBaseUser);
			
			//-----------------------------------------------
			//set up test
			
			User updateUser = new User();
			updateUser.setId(1);
			updateUser.setName("userNewName");
			updateUser.setLastname("userNewLastName");
			updateUser.setEmail("userNewEmail@mail.com");
			updateUser.setPassword("NewValidPassword1!");
			updateUser.setRole("cust");
			
			Mockito.when(this.dao.findById(updateUser.getId())).thenReturn(optionalDatabaseUser);
			Mockito.when(this.dao.save(updateUser)).thenReturn(updateUser);
			
			//-----------------------------------------------
			//execute action
			
			this.userBLL.saveOrUpdate(updateUser);
			
			//-----------------------------------------------
			//assert
			
			assertAll("update user with different valids credentials should return updated user",
				    () -> assertNotEquals(updateUser.getName(), dataBaseUser.getName()),
				    () -> assertNotEquals(updateUser.getLastname(), dataBaseUser.getLastname()),
				    () -> assertNotEquals(updateUser.getEmail(), dataBaseUser.getEmail()),
				    () -> assertNotEquals(updateUser.getRole(), dataBaseUser.getRole()),
				    () -> assertNotEquals(updateUser.getPassword(), dataBaseUser.getPassword())
				);
		}
		
		//-----------------------------------
		
		@Test
		void saveOrUpdate_withUserIdThatDoNotExist_throwBLLException() throws DALException, BLLException
		{
			//-----------------------------------------------
			//set up mock
			
			Optional<User> noUser = Optional.empty();
			
			//-----------------------------------------------
			//set up test
			
			User userthatDoNotExist = new User();
			userthatDoNotExist.setId(2);
			userthatDoNotExist.setName("userNewName");
			userthatDoNotExist.setLastname("userNewLastName");
			userthatDoNotExist.setEmail("userNewEmail@mail.com");
			userthatDoNotExist.setPassword("NewValidPassword1!");
			userthatDoNotExist.setRole("cust");
			
			
			Mockito.when(this.dao.findById(userthatDoNotExist.getId())).thenReturn(noUser);
			
			//-----------------------------------------------
			//assert
			
			assertThrows(BLLException.class, ()-> this.userBLL.saveOrUpdate(userthatDoNotExist), "update with id that do not existe should throw BLLException");
			
			
		}
		
	
		
	}



}
