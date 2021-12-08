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

		assertEquals("{\"message\":\"success\"}", postController.createPost(post, user.getUsername(), null));
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
	
	/* @Test
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
	} */

	@Test
	public void testCreateComment() {
		User user = new User("TestUser", "test@gmail.com", "testpassword");
		Post post1 = new Post("TestMessage1", "TestTitle1");
		Post post2 = new Post("TestMessage2", "TestTitle2");
		
		when(postRepository.findById(post1.getId())).thenReturn(post1);
		when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

		assertEquals("{\"message\":\"success\"}", postController.createCommentWithoutMedia(post2, user.getUsername(), post1.getId()));
	}

	@Test
	public void testGetComments() {
		User user = new User("TestUser", "test@gmail.com", "testpassword");
		User user2 = new User("TestUser2", "test2@gmail.com", "testpassword2");
		Post post1 = new Post("TestMessage1", "TestTitle1");
		Post post2 = new Post("TestMessage2", "TestTitle2");
		
		when(postRepository.findById(post1.getId())).thenReturn(post1);
		when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
		
		postController.createCommentWithoutMedia(post2, user.getUsername(), post1.getId());
		//post1.addChild(post2);

		List<Post> children = new ArrayList<>();
		children.add(post2);
		when(post1.getChildren()).thenReturn(children);

		postController.createCommentWithoutMedia(post2, user.getUsername(), post1.getId());

		assertEquals(children, postController.getAllComments(post1.getId()));
		
	}

	@Test
	public void testUpdatePost() {
		Post post1 = new Post("TestMessage1", "TestTitle1");
		Post post2 = new Post("TestMessage2", "TestTitle2");
		
		when(postRepository.findById(post1.getId())).thenReturn(post1);
		when(postRepository.findById(post2.getId())).thenReturn(post2);

		assertEquals(post2, postController.updatePost(post1.getId(), post2));
	}
	
	public void testUpdatePost2() {
		Post post1 = new Post("TestMessage1", "TestTitle1");
		Post post2 = new Post("TestMessage2", "TestTitle2");
		Post post3 = new Post("TestMessage3", "TestTitle3");
		
		when(postRepository.findById(post1.getId())).thenReturn(post1);
		when(postRepository.findById(post2.getId())).thenReturn(post2);
		
		postController.updatePost(post1.getId(), post2);
		
		assertEquals(post3, postController.updatePost(post2.getId(), post3));
	}
	
	public void testUpdatePLevel() {
		User user = new User("TestUser", "test@email.com", "test123", 0);

		when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

		ObjectMapper mapper = new ObjectMapper();

		ObjectNode obn = mapper.createObjectNode();
		obn.set("pLevel", mapper.convertValue("2", JsonNode.class));

		assertEquals("{\"message\":\"success\"}", userController.updateUserPLevel(user.getUsername(), obn));
	}

	
	
	
	
	
} 
