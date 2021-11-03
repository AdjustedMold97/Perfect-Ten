package com.example.usertest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import org.aspectj.lang.annotation.Before;
import org.json.JSONObject;
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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

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

		assertEquals(user, userController.getUserByUsername("Tony"));
		assertEquals(user2, userController.getUserByUsername("James"));
	}

	@Test
	public void createUserTest() {
		User user = new User("TestUser", "test@gmail.com", "testpassword");

		when(userRepository.findByUsername("TestUser")).thenReturn(null);

		assertEquals("{\"message\":\"success\"}", userController.createUser(user));
	}

	@Test
	public void createUserTestNull() {
		User user = new User("", "", "");

		assertEquals("{\"message\":\"error2\"}", userController.createUser(user));
	}

	@Test
	public void addFriendTest() {
		User user = new User("user1", "user1@gmail.com", "password1");
		User user2 = new User("user2", "user2@gmail.com", "password2");

		userRepository.save(user);
		userRepository.save(user2);

		ObjectMapper mapper = new ObjectMapper();

		ObjectNode obn = mapper.createObjectNode();
		obn.set("user", mapper.convertValue(user2.getUsername(), JsonNode.class));
		System.out.println(obn);

		assertEquals("{\"message\":\"success\"}", userController.addFriend(user.getUsername(), obn));
		assertEquals(true, user.isFriendsWith(user2));
		assertEquals(true, user2.isFriendsWith(user));
	}

} 
