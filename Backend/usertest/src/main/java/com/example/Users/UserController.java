package com.example.Users;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Posts.Post;
import com.example.Posts.PostRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"error1\"}";
    private String usernameFail = "{\"message\":\"error2\"}";
    private String emailFail = "{\"message\":\"error3\"}";
    private String passFail = "{\"message\":\"error4\"}";
    private String frenFail = "{\"message\":\"error5\"}";
    
    // Checks username and password with Users in UserRepository, returns success or failure
    @PostMapping(path = "/login")
    public String login(@RequestBody ObjectNode json) {
        // If username is null, return failure
        if(userRepository.findByUsername(json.get("username").textValue()) == null)
    		return usernameFail;
        // Check credentials and return either success or failure
    	if(userRepository.findByUsername(json.get("username").textValue()).getPassword().equals(json.get("password").textValue()))
            return success;
    	else 
    		return failure;
    } 
    
    // Returns list of posts for a specific user's username
    @GetMapping(path = "user/{user}/posts/list")
    public List<Post> getPostsByUsername(@PathVariable String user){
    	return userRepository.findByUsername(user).getPosts();
    }
    
    // Returns User associated with a username
    @GetMapping(path = "/user/{user}")
    public
    User getUserByUsername(@PathVariable String user) {
    	return userRepository.findByUsername(user);
    }
    
    // Returns list of all Users in database
    @GetMapping(path = "/user/list")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Returns User associated with a user ID
    @GetMapping(path = "/user/id/{id}")
    public User getUserById(@PathVariable int id) {
        return userRepository.findById(id);
    }

    // Creates a new User and stores it in database (signup)
    @PostMapping(path = "/user/new")
    public String createUser(@RequestBody User user) {
        // If RequestBody is null, return failure
        if (user == null) {
            return failure;
        }

        // If username field is null or empty or User with that username exists, return failure
        if(user.getUsername() == null || user.getUsername() == "" || userRepository.findByUsername(user.getUsername()) != null) {
        	return usernameFail;
        }
        
        // If email field is null or empty, return failure
        if(user.getEmail() == null || user.getEmail() == "") {
        	return emailFail;
        }
        
        // If password field is null or empty, return failure
        if (user.getPassword() == null || user.getPassword() == "") {
            return passFail;
        }

        // Save new User to database and return success
        userRepository.save(user);
        return success;
    }

    // Updates existing user with new attributes
    @PutMapping(path = "/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User request) {
        User user = userRepository.findById(id);
        // If requested user is not in database, return null
        if (user == null) {
            return null;
        }
        
        // If new username is the same as old username, return null
        if(!user.getUsername().equals(request.getUsername())) {
        	return null;
        }
        
        // Update attributes and return new user
        List<Post> userPosts = user.getPosts();
        deleteUser(id);
        request.setPosts(userPosts);
        userRepository.save(request);
        request.setId(id);
        return userRepository.findById(id);
    }

    // Assigns post to user
    @PutMapping(path = "/user/{user}/posts/{postId}")
    public String assignPostToUser(@PathVariable String user, @PathVariable int postId) {
        User temp = userRepository.findByUsername(user);
        Post post = null; 
        if (temp == null || post == null) {
            return failure;
        }
        post.setUser(temp);
        temp.addPost(post);
        userRepository.save(temp);
        return success;
    }
    
    // Assigns post to user with user ID
    @PutMapping(path = "/user/id/{user}/posts/{postId}")
    public String assignPostToUserID(@PathVariable int user, @PathVariable int postId) {
        User temp = userRepository.findById(user);
        Post post = null; 
        if (temp == null || post == null) {
            return failure;
        }
        post.setUser(temp);
        temp.addPost(post);
        userRepository.save(temp);
        return success;
    }

    // Deletes user from database
    @DeleteMapping(path = "/user/rm/{id}")
    public String deleteUser(@PathVariable int id) {
        // If User is not found in database, return failure
        if (userRepository.findById(id) == null) {
            return failure;
        }
        // Else, delete user and return success
        userRepository.deleteById(id);
        return success;
    }

    // Adds user2 to user1's friends list and vice versa
    @PostMapping(path = "/user/{user1}/friends/new")
    public String addFriend(@PathVariable String user1, @RequestBody ObjectNode json) {
        User firstUser = userRepository.findByUsername(user1);
        User secondUser = userRepository.findByUsername(json.get("user").textValue());

        // If either User is not found in database, return failure
        if (firstUser == null || secondUser == null) {
            return failure;
        }

        // If requested usernames are the same, return failure
        if (user1.equals(json.get("user").textValue())) {
            return usernameFail;
        }

        // If the two Users are already friends, return failure
        if (firstUser.isFriendsWith(secondUser)) {
            return frenFail;
        }

        // Add users to each other's friends list and return success
        firstUser.addFriend(secondUser);
        secondUser.addFriend(firstUser);

        userRepository.save(firstUser);
        userRepository.save(secondUser);

        return success;
    }

    // Returns a User's friends list as a List of Users
    @GetMapping(path = "/user/{user}/friends/list")
    public List<User> getFriendsByUsername(@PathVariable String user) {
        return userRepository.findByUsername(user).getFriends();
    }

    // Return's a User's friends list as a List of Strings (usernames)
    @GetMapping(path = "/user/{user}/friends/list/usernames")
    public List<String> getFriendUsernamesByUsername(@PathVariable String user) {
        List<String> usernames = new ArrayList<>();
        List<User> friends = userRepository.findByUsername(user).getFriends();
        
        // Loop through friends list and retrieve each username
        for (int i = 0; i < friends.size(); i++) {
            usernames.add(friends.get(i).getUsername());
        }

        return usernames;
    }

    // Removes a friend from a User's friends list
    @PutMapping(path = "/user/{user1}/friends/remove")
    public String removeFriend(@PathVariable String user1, @RequestBody ObjectNode json) {
        User firstUser = userRepository.findByUsername(user1);
        User secondUser = userRepository.findByUsername(json.get("user").textValue());

        // If either User is not found in database, return failure
        if (firstUser == null || secondUser == null) {
            return failure;
        }  

        // If both usernames are the same, return failure
        if (user1.equals(json.get("user").textValue())) {
            return failure;
        } 

        // If first User is not friends with second User, return failure
        if (!firstUser.isFriendsWith(secondUser)) {
            return failure;
        }

        // Remove Users from each other's friends list and return success
        firstUser.removeFriend(secondUser);
        secondUser.removeFriend(firstUser);

        userRepository.save(firstUser);
        userRepository.save(secondUser);

        return success;
    }

    // Returns a User's list of blocked Users
    @GetMapping(path = "/user/{user}/blocked/list")
    public List<User> getBlockedUsersByUsername(@PathVariable String user) {
        return userRepository.findByUsername(user).getBlockedUsers();
    }

    // Returns a User's list of blocked Users as a list of Strings (usernames)
    @GetMapping(path = "/user/{user}/blocked/list/usernames")
    public List<String> getBlockedUsernamesByUsername(@PathVariable String user) {
        List<String> usernames = new ArrayList<>();
        List<User> blocked = userRepository.findByUsername(user).getBlockedUsers();

        // Loop through blocked list and retrieve each username
        for (int i = 0; i < blocked.size(); i++) {
            usernames.add(blocked.get(i).getUsername());
        }

        return usernames;
    }

    // Adds user2 to user1's blocked list
    @PostMapping(path = "/user/{user1}/blocked/new")
    public String blockUser(@PathVariable String user1, @RequestBody ObjectNode json) {
        User firstUser = userRepository.findByUsername(user1);
        User secondUser = userRepository.findByUsername(json.get("user").textValue());

        // If either User is not found in databse, return failure
        if (firstUser == null || secondUser == null) {
            return failure;
        }

        // If both usernames are the same, return failure
        if(user1.equals(json.get("user").textValue())) {
            return failure;
        }

        // If first User is already blocking second User, return failure
        if(firstUser.isBlocking(secondUser)) {
            return failure;
        }

        // Block second User and save result to database, and return success
        firstUser.addBlockedUser(secondUser);
        userRepository.save(firstUser);
        return success;
    }

    // Removes user2 from user1's blocked list
    @PutMapping(path = "/user/{user1}/blocked/remove")
    public String unblockUser(@PathVariable String user1, @RequestBody ObjectNode json) {
        User firstUser = userRepository.findByUsername(user1);
        User secondUser = userRepository.findByUsername(json.get("user").textValue());

        // If either User is not found in database, return failure
        if (firstUser == null || secondUser == null) {
            return failure;
        }

        // If requested usernames are identical, return failure
        if (user1.equals(json.get("user").textValue())) {
            return failure;
        }

        // If first User is not blocking second User, return failure
        if (!firstUser.isBlocking(secondUser)) {
            return failure;
        }

        // Unblock second User, save result in database, and return success
        firstUser.removeBlockedUser(secondUser);
        userRepository.save(firstUser);
        return success;
    }
}
