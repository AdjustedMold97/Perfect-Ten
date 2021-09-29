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

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/users")
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/users/{id}")
    User getUserById(@PathVariable int id) {
        return userRepository.findById(id);
    }

    @PostMapping(path = "/users")
    String createUser(@RequestBody User user) {
        if (user == null) {
            return failure;
        }
        userRepository.save(user);
        return success;
    }

    @PutMapping("/users/{id}")
    User updateUser(@PathVariable int id, @RequestBody User request) {
        User user = userRepository.findById(id);
        if (user == null) {
            return null;
        }
        userRepository.save(request);
        return userRepository.findById(id);
    }

    @PutMapping("/users/{userId}/posts/{postId}")
    String assignPostToUser(@PathVariable int userId, @PathVariable int postId) {
        User user = userRepository.findById(userId);
        Post post = null; 
        if (user == null || post == null) {
            return failure;
        }
        post.setUser(user);
        user.addPost(post);
        userRepository.save(user);
        return success;
    }

    @DeleteMapping(path = "/users/{id}")
    String deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
        return success;
    }
}
