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
import java.util.Base64;

import com.example.Main;
import com.example.Users.*;
import com.example.Posts.*;
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

		assertEquals("{\"message\":\"success\"}", userController.storeExistingUser(user));
	}

	@Test
	public void createUserTestNull() {
		User user = new User("", "", "");

		assertEquals("{\"message\":\"error2\"}", userController.storeExistingUser(user));
	}

	@Test
	public void addFriendTest() {
		User user1 = new User("user1", "user1@gmail.com", "password1");
		User user2 = new User("user2", "user2@gmail.com", "password2");

		when(userRepository.findByUsername("user1")).thenReturn(user1);
		when(userRepository.findByUsername("user2")).thenReturn(user2);

		ObjectMapper mapper = new ObjectMapper();

		ObjectNode obn = mapper.createObjectNode();
		obn.set("user", mapper.convertValue(user2.getUsername(), JsonNode.class));
		
		// Check that users are not friends before addFriend function is called
		assertEquals(false, user1.isFriendsWith(user2));
		assertEquals(false, user2.isFriendsWith(user1));

		// Call addFriend and make sure success is returned
		assertEquals("{\"message\":\"success\"}", userController.addFriend(user1.getUsername(), obn));

		// Check that users are friends after addFriend function is called
		assertEquals(true, user1.isFriendsWith(user2));
		assertEquals(true, user2.isFriendsWith(user1));
	}

	@Test
	public void loginTest() {
		User user = new User("test123", "test123@gmail.com", "testpassword123");

		userController.storeExistingUser(user);

		ObjectMapper mapper = new ObjectMapper();

		ObjectNode obn = mapper.createObjectNode();
		obn.set("username", mapper.convertValue(user.getUsername(), JsonNode.class));
		obn.set("password", mapper.convertValue(user.getPassword(), JsonNode.class));

		// ObjectNode for fake user not in database
		ObjectNode obn2 = mapper.createObjectNode();
		obn2.set("username", mapper.convertValue("fake", JsonNode.class));
		obn2.set("password", mapper.convertValue("fakepassword", JsonNode.class));


		when(userRepository.findByUsername("test123")).thenReturn(user);
		when(userRepository.findByUsername("fake")).thenReturn(null);
		assertEquals("{\"message\":\"success\"}", userController.login(obn));
		assertEquals("{\"message\":\"error2\"}", userController.login(obn2));
	}

	@Test
	public void getFriendUsernamesTest() {
		User user1 = new User("user1", "user1@gmail.com", "password1");
		User user2 = new User("user2", "user2@gmail.com", "password2");
		User user3 = new User("user3", "user3@gmail.com", "password3");
		User user4 = new User("user4", "user4@gmail.com", "password4");

		when(userRepository.findByUsername("user1")).thenReturn(user1);
		when(userRepository.findByUsername("user2")).thenReturn(user2);
		when(userRepository.findByUsername("user3")).thenReturn(user3);
		when(userRepository.findByUsername("user4")).thenReturn(user4);

		ObjectMapper mapper = new ObjectMapper();

		ObjectNode obn = mapper.createObjectNode();
		obn.set("user", mapper.convertValue(user2.getUsername(), JsonNode.class));

		ObjectNode obn2 = mapper.createObjectNode();
		obn2.set("user", mapper.convertValue(user2.getUsername(), JsonNode.class));

		ObjectNode obn3 = mapper.createObjectNode();
		obn3.set("user", mapper.convertValue(user3.getUsername(), JsonNode.class));

		ObjectNode obn4 = mapper.createObjectNode();
		obn4.set("user", mapper.convertValue(user4.getUsername(), JsonNode.class));

		userController.addFriend("user1", obn2);
		userController.addFriend("user1", obn3);
		userController.addFriend("user1", obn4);
		userController.addFriend("user2", obn3);

		// Verify that addFriend calls findByUsername once with argument "user4"
		verify(userRepository).findByUsername("user4");
		// Verify that addFriend calls findByUsername three times with argument "user1"
		verify(userRepository, times(3)).findByUsername("user1");

		List<String> friendsOfUser1 = new ArrayList<>();
		friendsOfUser1.add("user2");
		friendsOfUser1.add("user3");
		friendsOfUser1.add("user4");

		// Check that getFriendUsernamesByUsername returns correct list of usernames
		assertEquals(friendsOfUser1, userController.getFriendUsernamesByUsername("user1"));
	}
	
	@Mock
	private PostRepository postRepository;
	
	@InjectMocks
	PostController postController;
	
	@Test
	void initMocks2() {
		postRepository = mock(PostRepository.class);
	}

	@Test
	public void createPostTest() {
		User user = new User("TestUser", "test@gmail.com", "testpassword");
		Post post = new Post("TestMessage", "TestTitle", user);

		when(userRepository.findByUsername("TestUser")).thenReturn(user);

		assertEquals("{\"message\":\"success\"}", postController.createPost(post, user.getUsername()));
	}
	
	@Test
	public void getPostTest() {
		User user = new User("TestUser", "test@gmail.com", "testpassword");
		Post post1 = new Post("TestMessage1", "TestTitle1", user);
		Post post2 = new Post("TestMessage2", "TestTitle2", user);
		
		when(postRepository.findById(1)).thenReturn(post1);
		when(postRepository.findById(2)).thenReturn(post2);
		
		assertEquals(post1, postController.getPostById(1));
		assertEquals(post2, postController.getPostById(2));
	}
	
	@Test
	public void getCommentTest() {
		User user = new User("TestUser", "test@gmail.com", "testpassword");
		Post post1 = new Post("TestMessage1", "TestTitle1", user);
		Post post2 = new Post("TestMessage2", "TestTitle2", user);
		post1.addComment("TestComment", user);
		
		when(postRepository.findById(1)).thenReturn(post1);
		when(postRepository.findById(2)).thenReturn(post2);
		
		assertEquals(post1.getComment(0), postController.getCommentById(1,0));
		assertEquals("{\"message\":\"error1\"}", postController.getCommentById(1,1));
		assertEquals("{\"message\":\"error1\"}", postController.getCommentById(2,0));
	}
	
	@Test
	public void CreateCommentTest() {
		User user = new User("TestUser", "test@gmail.com", "testpassword");
		Post post1 = new Post("TestMessage1", "TestTitle1", user);
		Post post2 = new Post("TestMessage2", "TestTitle2", user);
		post1.addComment("TestComment", user);
		
		when(userRepository.findByUsername("TestUser")).thenReturn(user);
		when(postRepository.findById(1)).thenReturn(post1);
		when(postRepository.findById(2)).thenReturn(post2);
		
		ObjectMapper mapper = new ObjectMapper();

		ObjectNode obn = mapper.createObjectNode();
		obn.set("id", mapper.convertValue(1, JsonNode.class));
		obn.set("message", mapper.convertValue("Test", JsonNode.class));

		assertEquals("{\"message\":\"success\"}", postController.createComment(obn, "TestUser"));
	}

	@Test
	public void testPicUpload() {
		String file = "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";
		User user = new User("Jeff", "jeff@gmail.com", "jeff123");
		userRepository.save(user);
		byte[] bytes = Base64.getDecoder().decode(file);
		System.out.println(bytes);
		//userController.setUserProfilePicBytes(user.getUsername(), file);
	}

} 
