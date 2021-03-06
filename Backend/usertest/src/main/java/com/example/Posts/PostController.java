package com.example.Posts;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import java.sql.Blob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.Users.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.mysql.cj.jdbc.Blob;

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

    @PostMapping(path = "/posts/new/{username}")
    public String createPostWithoutMedia(@RequestBody Post post, @PathVariable String username) {
        if (post == null || userRepository.findByUsername(username) == null)
            return usernameFail;
        // User user = userRepository.findByUsername(username);
        post.setUser(userRepository.findByUsername(username));
        post.setTime();
        post.setIsAChild(false);

        postRepository.save(post);
        userRepository.findByUsername(username).addPost(post);
        userRepository.save(userRepository.findByUsername(username));
        return success;
    }

    /**
     * Makes a new user by specified user
     * @param post
     * @param username
     * @return String
     */
    @ApiOperation(value = "Makes a new user by specified user", response = String.class)
    @PostMapping(path = "/posts/new2/{username}")
    public String createPost(@RequestBody Post post, @PathVariable String username, @RequestParam String file){
        if (post == null || userRepository.findByUsername(username) == null)
            return usernameFail;
        // User user = userRepository.findByUsername(username);
        post.setUser(userRepository.findByUsername(username));
        post.setTime();
        post.setIsAChild(false);

        // Update post's extension and media fields
        if (file != null) {
            byte[] bytes = Base64.getDecoder().decode(file);

            try {
                SerialBlob blob = new SerialBlob(bytes);
                Blob image = blob;
                post.setExtension("post" + post.getId() + "-media.png");
                post.setMedia(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        postRepository.save(post);
        userRepository.findByUsername(username).addPost(post);
        userRepository.save(userRepository.findByUsername(username));
        return success;
    }

    @PostMapping(path = "/posts/{id}/comment/{username}")
    public String createCommentWithoutMedia(@RequestBody Post post, @PathVariable String username, @PathVariable int id) {
        if (post == null || userRepository.findByUsername(username) == null)
            return usernameFail;

        if (postRepository.findById(id) == null) {
            return failure;
        }
        // User user = userRepository.findByUsername(username);
        post.setUser(userRepository.findByUsername(username));
        post.setTime();
        post.setIsAChild(true);

        postRepository.save(post);
        userRepository.findByUsername(username).addPost(post);
        userRepository.save(userRepository.findByUsername(username));
        postRepository.findById(id).addChild(post);
        postRepository.save(postRepository.findById(id));
        return success;
    }

    
    /**
     * Make a new comment on a specified comment by id by the specifiyed user
     * @param idAndMessage
     * @param username
     * @return String
     */
    @ApiOperation(value = "Make a new comment on a specified comment by id by the specifiyed user", response = String.class)
    @PostMapping(path = "/posts/{id}/comment2/{username}")
    public String createComment(@RequestBody Post post, @PathVariable String username, @PathVariable int id, @RequestParam String file) {
    	if (post == null || userRepository.findByUsername(username) == null)
            return usernameFail;

        if (postRepository.findById(id) == null) {
            return failure;
        }
        // User user = userRepository.findByUsername(username);
        post.setUser(userRepository.findByUsername(username));
        post.setTime();
        post.setIsAChild(true);

        // Update comment's extension and media fields
        if (file != null) {
            byte[] bytes = Base64.getDecoder().decode(file);

            try {
                SerialBlob blob = new SerialBlob(bytes);
                Blob image = blob;
                post.setExtension("post" + post.getId() + "-media.png");
                post.setMedia(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        postRepository.save(post);
        userRepository.findByUsername(username).addPost(post);
        userRepository.save(userRepository.findByUsername(username));
        postRepository.findById(id).addChild(post);
        postRepository.save(postRepository.findById(id));
        return success;
    }
    
    /**
     * Gets all comments by the id of parent post
     * @param id
     * @return String
     */
    @ApiOperation(value = "Gets all comments by the id of parent post", response = String.class)
    @GetMapping(path = "/posts/{id}/all")
    public List<Post> getAllComments( @PathVariable int id){
    	return postRepository.findById(id).getChildren();
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
    	/* if(postRepository.findById(id).getChildren().size() != 0) {
    		int temp = postRepository.findById(id).getChildren().size();
    		for(int i= temp - 1; i >= 0; i--) {
    			deletePost(postRepository.findById(id).getChildren().get(i).getId());
    		}
    	} */
    	//long newid = (long)id;

        int newId = (int) id;

        User user = userRepository.findByUsername(postRepository.findById(newId).getUname());
        user.removePost(postRepository.findById(newId));

        if (postRepository.findById(newId).getIsAChild()) {
            List<Post> otherPosts = postRepository.findAll();
            for (Post post : otherPosts) {
                if (post.getChildren().contains(postRepository.findById(newId))) {
                    post.removeChild(postRepository.findById(newId));
                }
            }
        }

        postRepository.deleteById(newId);
        return success;
    }
}
