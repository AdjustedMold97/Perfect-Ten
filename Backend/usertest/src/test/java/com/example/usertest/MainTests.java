package com.example.usertest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.ArrayList;

import com.example.Main;
import com.example.Users.*;

@SpringBootTest(classes = MainTests.class)
class MainTests {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	UserController userController;

	@Test
	void initMocks() {
		userRepository = mock(UserRepository.class);
	}

	@Test
	public void getUserByUsernameTest() {
		User user = new User("Tony", "tony@gmail.com", "password1");
		User user2 = new User("James", "james@gmail.com", "password2");

		when(userRepository.findByUsername("Tony")).thenReturn(user);
		when(userRepository.findByUsername("James")).thenReturn(user2);

		assertEquals(userController.getUserByUsername("Tony"), user);
		assertEquals(userController.getUserByUsername("James"), user2);
	}

	@Test
	public void createUserTest() {
		User user = new User("TestUser", "test@gmail.com", "testpassword");

		when(userRepository.findByUsername("TestUser")).thenReturn(null);

		assertEquals(userController.createUser(user), "{\"message\":\"success\"}");
	}

	@Test
	public void createUserTestNull() {
		User user = new User("", "", "");

		assertEquals(userController.createUser(user), "{\"message\":\"error2\"}");
	}

} 
