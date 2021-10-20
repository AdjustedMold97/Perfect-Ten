package com.example.Posts;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.Users.*;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"error1\"}";
    private String usernameFail = "{\"message\":\"error2\"}";;
    private String emailFail = "{\"message\":\"error3\"}";;
    private String passFail = "{\"message\":\"error4\"}";;

    @GetMapping(path = "/posts/list")
    List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    @GetMapping(path = "/posts/{id}")
    Post getPostById( @PathVariable int id){
        return postRepository.findById(id);
    }

    @PostMapping(path = "/posts/new/{username}")
    String createPost(@RequestBody Post post, @PathVariable String username){
        if (post == null || userRepository.findByUsername(username) == null)
            return usernameFail;
        // User user = userRepository.findByUsername(username);
        post.setUser(userRepository.findByUsername(username));
        postRepository.save(post);
        userRepository.findByUsername(username).addPost(post);
        userRepository.save(userRepository.findByUsername(username));
        return success;
    }

    @PutMapping("/posts/{id}")
    Post updatePost(@PathVariable int id, @RequestBody Post request){
        Post post = postRepository.findById(id);
        if(post == null)
            return null;
        postRepository.save(request);
        return postRepository.findById(id);
    } 
    
    @DeleteMapping(path = "/posts/rm/{id}")
    String deletePost(@PathVariable long id){
    	postRepository.deleteById(id);
    	return success;
    }
}
