package com.example.Users;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    private String failure = "{\"message\":\"failure\"}";

    
    @GetMapping(path = "/login")
    String login(@RequestBody ObjectNode json) {
        if(userRepository.findByUsername(json.get("username").textValue()) == null)
    		return "User with username " + json.get("username").textValue() + " not found";
    	if(userRepository.findByUsername(json.get("username").textValue()).getPassword().equals(json.get("password").textValue()))
    		return "yes";
    	else 
    		return "no";
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

        if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null) {
            return failure;
        }

        if (user.getUsername() == "" || user.getEmail() == "" || user.getPassword() == "") {
            return failure;
        }        

        if(userRepository.findByUsername(user.getUsername()) != null) {
            return failure + ": user with username \" " + user.getUsername() + "\" already exists";
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
}
