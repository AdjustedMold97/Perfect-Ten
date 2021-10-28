package com.example.Users;

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
    private String usernameFail = "{\"message\":\"error2\"}";;
    private String emailFail = "{\"message\":\"error3\"}";;
    private String passFail = "{\"message\":\"error4\"}";;
    
    @PostMapping(path = "/login")
    String login(@RequestBody ObjectNode json) {
        if(userRepository.findByUsername(json.get("username").textValue()) == null)
    		return usernameFail;
    	if(userRepository.findByUsername(json.get("username").textValue()).getPassword().equals(json.get("password").textValue()))
            return success;
    	else 
    		return failure;
    } 
    
    @GetMapping(path = "user/{user}/posts/list")
    List<Post> getPostsByusername(@PathVariable String user){
    	return userRepository.findByUsername(user).getPosts();
    }
    
    @GetMapping(path = "/user/{user}")
    User getUserByUsername(@PathVariable String user) {
    	return userRepository.findByUsername(user);
    }
    
    @GetMapping(path = "/user/list")
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/user/id/{id}")
    User getUserById(@PathVariable int id) {
        return userRepository.findById(id);
    }

    @PostMapping(path = "/user/new")
    String createUser(@RequestBody User user) {
        if (user == null) {
            return failure;
        }

        if(user.getUsername() == null || user.getUsername() == "" || userRepository.findByUsername(user.getUsername()) != null) {
        	return usernameFail;
        }
        
        if(user.getEmail() == null || user.getEmail() == "") {
        	return emailFail;
        }
        
        if (user.getPassword() == null || user.getPassword() == "") {
            return passFail;
        }

        userRepository.save(user);
        return success;
    }


    @PutMapping(path = "/user/{id}")
    User updateUser(@PathVariable int id, @RequestBody User request) {
        User user = userRepository.findById(id);
        if (user == null) {
            return null;
        }
        
        if(!user.getUsername().equals(request.getUsername())) {
        	return null;
        }
        
        List<Post> userPosts = user.getPosts();
        deleteUser(id);
        request.setPosts(userPosts);
        userRepository.save(request);
        request.setId(id);
        return userRepository.findById(id);
    }

    @PutMapping(path = "/user/{user}/posts/{postId}")
    String assignPostToUser(@PathVariable String user, @PathVariable int postId) {
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
    
    @PutMapping(path = "/user/id/{user}/posts/{postId}")
    String assignPostToUserID(@PathVariable int user, @PathVariable int postId) {
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

    @DeleteMapping(path = "/user/rm/{id}")
    String deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
        return success;
    }

    @PostMapping(path = "/user/{user1}/friends/new")
    String addFriend(@PathVariable String user1, @RequestBody String user2) {
        User firstUser = userRepository.findByUsername(user1);
        User secondUser = userRepository.findByUsername(user2);

        if (firstUser == null || secondUser == null) {
            return failure;
        }

        if (user1.equals(user2)) {
            return failure;
        }

        firstUser.addFriend(secondUser);
        secondUser.addFriend(firstUser);

        userRepository.save(firstUser);
        userRepository.save(secondUser);

        return success;
    }

    @GetMapping(path = "/user/{user}/friends/list")
    List<User> getFriendsByUsername(@PathVariable String user) {
        return userRepository.findByUsername(user).getFriends();
    }
}
