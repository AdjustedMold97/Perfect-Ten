package com.example.Posts;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.Posts.*;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/phones")
    List<Post> getAllPhones(){
        return postRepository.findAll();
    }

    @GetMapping(path = "/phones/{id}")
    Post getPhoneById( @PathVariable int id){
        return postRepository.findById(id);
    }

    @PostMapping(path = "/phones")
    String createPost(Post post){
        if (post == null)
            return failure;
        postRepository.save(post);
        return success;
    }

    @PutMapping("/phones/{id}")
    Post updatePhone(@PathVariable int id, @RequestBody Post request){
        Post post = postRepository.findById(id);
        if(post == null)
            return null;
        postRepository.save(request);
        return postRepository.findById(id);
    } 
      
}
