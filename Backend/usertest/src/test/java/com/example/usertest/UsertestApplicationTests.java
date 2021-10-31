package com.example.usertest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.ArrayList;

import com.example.Users.*;

@SpringBootTest
class MainTests {
	/*
	@Test
	void contextLoads() {
	} */

	/*
	@Test
	public void addFriendTest() {
		User bob = mock(User.class);
		User tom = mock(User.class);

		List<User> bobFriends = new ArrayList<>();
		bobFriends.add(tom);
		when(bob.getFriends()).thenReturn(bobFriends);

	} */

	@InjectMocks
	UserController userController;

	@Test
	public void getUserByIdTest() {
		UserRepository userRepository = mock(UserRepository.class);

		when(userRepository.findById(1)).thenReturn(new User("Bob", "bob@gmail.com", "password1"));

		User user = userController.getUserById(1);

		assertEquals("Bob", user.getUsername());
		assertEquals("bob@gmail.com", user.getEmail());
		assertEquals("password1", user.getPassword());
	}

} 
