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

import io.swagger.annotations.*;

/**
 * @author John Barmen
 */

@Api(value = "PostController", description = "Rest API is related to post entity")
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

    /**
     * Get List of jsons representing all posts
     * @return List of Post
     */
    @ApiOperation(value = "Get List of jsons representing all posts", response = List.class)
    @GetMapping(path = "/posts/list")
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    /**
     * Gets a specific post by its ID
     * @param id
     * @return Post
     */
    @ApiOperation(value = "Gets a specific post by its ID", response = Post.class)
    @GetMapping(path = "/posts/{id}")
    public Post getPostById( @PathVariable int id){
        return postRepository.findById(id);
    }

    /**
     * Makes a new user by specified user
     * @param post
     * @param username
     * @return String
     */
    @ApiOperation(value = "Makes a new user by specified user", response = String.class)
    @PostMapping(path = "/posts/new/{username}")
    public String createPost(@RequestBody Post post, @PathVariable String username){
        if (post == null || userRepository.findByUsername(username) == null)
            return usernameFail;
        // User user = userRepository.findByUsername(username);
        post.setUser(userRepository.findByUsername(username));
        post.setTime();
        postRepository.save(post);
        userRepository.findByUsername(username).addPost(post);
        userRepository.save(userRepository.findByUsername(username));
        return success;
    }
    
    /**
     * Make a new comment on a specified comment by id by the specifiyed user
     * @param id
     * @param message
     * @param username
     * @return String
     */
    @ApiOperation(value = "Make a new comment on a specified comment by id by the specifiyed user", response = String.class)
    @PostMapping(path = "/posts/new/comment/{username}")
    public String createComment(@RequestBody int id, @RequestBody String message, @PathVariable String username) {
    	if (userRepository.findByUsername(username) == null)
            return usernameFail;
    	if(postRepository.findById(id) == null) 
    		return failure;
    	postRepository.findById(id).addComment(message, userRepository.findByUsername(username));
    	return success;
    }
    
    /**
     * Gets a comment by the id of parent post and its specific index
     * @param id
     * @param index
     * @return String
     */
    @ApiOperation(value = "Gets a comment by the id of parent post and its specific index", response = String.class)
    @GetMapping(path = "/posts/{id}/{index}")
    public String getCommentById( @PathVariable int id, @PathVariable int index){
        return postRepository.findById(id).getComment(index);
    }

    /**
     * Deletes a comment by id of parent post and its specific index
     * @param id
     * @param index
     * @return
     */
    @ApiOperation(value = "Deletes a comment by id of parent post and its specific index", response = String.class)
    @DeleteMapping(path = "/posts/rm/{id}/{index}")
    public String deleteComment(@PathVariable int id, @PathVariable int index){
    	postRepository.findById(id).delComment(index);
    	return success;
    }
    
    /**
     * Updates a post of given ID
     * @param id
     * @param request
     * @return Post
     */
    @ApiOperation(value = "Updates a post of given ID", response = Post.class)
    @PutMapping("/posts/{id}")
    public Post updatePost(@PathVariable int id, @RequestBody Post request){
        Post post = postRepository.findById(id);
        if(post == null)
            return null;
        postRepository.save(request);
        return postRepository.findById(id);
    } 
    
    /**
     * delete post by given ID
     * @param id
     * @return String
     */
    @ApiOperation(value = "delete post by given ID", response = String.class)
    @DeleteMapping(path = "/posts/rm/{id}")
    public String deletePost(@PathVariable long id){
    	postRepository.deleteById(id);
    	return success;
    }
}
