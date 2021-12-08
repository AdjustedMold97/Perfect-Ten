package com.example.Users;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.example.Posts.Post;
import com.example.Posts.PostController;
import com.example.Posts.PostRepository;
//import com.example.Users.User.PrivilegeLevel;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.ByteArrayOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

// import com.mysql.cj.jdbc.Blob;
import com.mysql.cj.jdbc.IterateBlock;

/**
 * This class contains all of the REST methods used to communicate with the database
 * @author Adam Sweiger
 */
@Api(value = "UserController", description = "REST APIs related to User Entity")
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
    
    /**
     * Checks username and password with Users in UserRepository, returns success or failure
     * @param json JSON Object with username and password key/value pairs
     * @return success if login credentials are correct, failure otherwise
     */
    @ApiOperation(value = "Compare username and password to Users in UserRepository, returns success or failure", response = String.class)
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
    
    /**
     * Returns list of posts for a specific user's username
     * @param user username of User
     * @return List representings User's posts
     */
    @ApiOperation(value = "Get a user's list of posts by their username", response = Iterable.class)
    @GetMapping(path = "user/{user}/posts/list")
    public List<Post> getPostsByUsername(@PathVariable String user){
    	return userRepository.findByUsername(user).getPosts();
    }
    
    /**
     * Returns User associated with a username
     * @param user username of requested User
     * @return Requested User
     */
    @ApiOperation(value = "Get all of a user's information by their username", response = User.class)
    @GetMapping(path = "/user/{user}")
    public
    User getUserByUsername(@PathVariable String user) {
    	return userRepository.findByUsername(user);
    }
    
    /**
     * Returns list of all Users in database
     * @return List representing all Users in database
     */
    @ApiOperation(value = "Get all users in the database", response = List.class)
    @GetMapping(path = "/user/list")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Returns list of all usernames in database
     * @return List representing usernames of all Users in database
     */
    @ApiOperation(value = "Get usernames of all users in the database", response = List.class)
    @GetMapping(path = "/user/list/usernames")
    public List<String> getAllUsernames() {
        List<User> users = userRepository.findAll();
        List<String> usernames = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            usernames.add(users.get(i).getUsername());
        }

        return usernames;
    }

    /**
     * Returns User associated with a user ID
     * @param id ID of User
     * @return User associated with id
     */
    @ApiOperation(value = "Get all of a user's information by their id", response = User.class)
    @GetMapping(path = "/user/id/{id}")
    public User getUserById(@PathVariable int id) {
        return userRepository.findById(id);
    }

    /**
     * Stores existing user in database (signup)
     * @param user User to be saved in database
     * @return success if User is saved, failure otherwise
     */
    @ApiOperation(value = "Saves an existing user to the database for signup, returns success or failure", response = String.class)
    @PostMapping(path = "/user/new")
    public String storeExistingUser(@RequestBody User user) {
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

    /**
     * Updates existing user with new attributes
     * @param id ID of User to be updated
     * @param request New User attributes
     * @return Updated User
     */
    @ApiOperation(value = "Updates existing user with new attribute values", response = User.class)
    @PutMapping(path = "/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User request) {
        User user = userRepository.findById(id);
        // If requested user is not in database, return null
        if (user == null) {
            return null;
        }
        
        // If new username is the same as old username, return null
        if(user.getUsername().equals(request.getUsername())) {
        	return null;
        }
        
        // Update attributes and return new user
        List<Post> userPosts = user.getPosts();
        List<User> friends = user.getFriends();
        List<User> blockedList = user.getBlockedUsers();
        
        deleteUser(user.getUsername());
        
        for (User friend: friends) {
            friend.addFriend(request);
            request.addFriend(friend);
        }

        for (User blocked : blockedList) {
            request.addBlockedUser(blocked);
        }

        List<User> otherUsers = userRepository.findAll();
        for (User other : otherUsers) {
            if (other.isBlocking(user)) {
                other.addBlockedUser(request);
            }
        }

        request.setPosts(userPosts);
        userRepository.save(request);
        //request.setId(id);
        return userRepository.findById(request.getId());
    }

    /**
     * Assigns post to user
     * @param user Username of User
     * @param postId ID of post to be assigned
     * @return success if post is assigned, otherwise failure
     */
    @ApiOperation(value = "Adds existing post to a user's post list, returns success or failure", response = String.class)
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
    
    /**
     * Assigns post to user with user ID
     * @param user Username of User
     * @param postId ID of post to be assigned
     * @return success if post is assigned, otherwise failure
     */
    @ApiOperation(value = "Assigns existing post to user by ID, returns success or failure", response = String.class)
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

    /**
     * Deletes user from database
     * @param id ID of User to be deleted
     * @return success if User is deleted, otherwise failure
     */
    @ApiOperation(value = "Deletes user from database, returns success or failure", response = String.class)
    @DeleteMapping(path = "/user/rm/{uname}")
    public String deleteUser(@PathVariable String uname) {
        // If User is not found in database, return failure
    	int id = userRepository.findByUsername(uname).getId();
        if (userRepository.findById(id) == null) {
            return failure;
        }

        User requestedUser = userRepository.findById(id);
        
        // Else, delete user's posts, remove them from other's lists and return success
        List<User> otherUsers = userRepository.findAll();
        for (User user : otherUsers) {
            if (user.isFriendsWith(requestedUser)) {
                user.removeFriend(requestedUser);
                requestedUser.removeFriend(user);
            }
            if (user.isBlocking(requestedUser)) {
                user.removeBlockedUser(requestedUser);
            }
            if (requestedUser.isBlocking(user)) {
                requestedUser.removeBlockedUser(user);
            }
        }

        List<Post> userPosts = requestedUser.getPosts();
        for (Post post : userPosts) {
            requestedUser.removePost(post);
            postRepository.deleteById(post.getId());
        }

        userRepository.deleteById(id);
        return success;
    }

    /**
     * Adds user2 to user1's friends list and vice versa
     * @param user1 Username of first User
     * @param json JSON Object with key/value pair representing username of second User
     * @return success if friendship is created, otherwise failure
     */
    @ApiOperation(value = "Creates friendship between two users, returns success or failure", response = String.class)
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

    /**
     * Returns a User's friends list as a List of Users
     * @param user Username of User
     * @return List representing User's friends
     */
    @ApiOperation(value = "Get a user's friends list as a list of users", response = Iterable.class)
    @GetMapping(path = "/user/{user}/friends/list")
    public List<User> getFriendsByUsername(@PathVariable String user) {
        return userRepository.findByUsername(user).getFriends();
    }

    /**
     * Return's a User's friends list as a List of Strings (usernames)
     * @param user Username of User
     * @return List representing usernames of friends
     */
    @ApiOperation(value = "Get a user's friends list as a list of usernames", response = Iterable.class)
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

    /**
     * Removes a friend from a User's friends list
     * @param user1 Username of first User
     * @param json JSON Object with key/value pair representing username of second User
     * @return success if friend is removed, otherwise failure
     */
    @ApiOperation(value = "Removes friendship between users, returns success or failure", response = String.class)
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

    /**
     * Returns a User's list of blocked Users
     * @param user Username of User
     * @return List representing users blocked by User
     */
    @ApiOperation(value = "Get a user's blocked list of usernames", response = Iterable.class)
    @GetMapping(path = "/user/{user}/blocked/list")
    public List<User> getBlockedUsersByUsername(@PathVariable String user) {
        return userRepository.findByUsername(user).getBlockedUsers();
    }

    /**
     * Returns a User's list of blocked Users as a list of Strings (usernames)
     * @param user Username of User
     * @return List representing usernames of blocked users
     */
    @ApiOperation(value = "Get a user's blocked list of users", response = Iterable.class)
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

    /**
     * Adds user2 to user1's blocked list
     * @param user1 Username of first User
     * @param json JSON Object with key/value pair representing username of second User
     * @return success if second User is blocked, otherwise failure
     */
    @ApiOperation(value = "Blocks a user, returns success or failure", response = String.class)
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

    /**
     * Removes user2 from user1's blocked list
     * @param user1 Username of first User
     * @param json JSON Object with key/value pair representing username of second User
     * @return success if second User is unblocked, otherwise failure
     */
    @ApiOperation(value = "Removes user from blocked list", response = String.class)
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

    @GetMapping(path = "/user/{user}/pic")
    public ResponseEntity<Resource> getUserProfilePic(@PathVariable String user) throws IOException, SQLException {
        User requestedUser = userRepository.findByUsername(user);

        if (requestedUser == null || requestedUser.getProfilePic() == null) {
            return null;
        }

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + requestedUser.getExtension());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        int blobLength = (int)requestedUser.getProfilePic().length();
        byte[] byteArray = requestedUser.getProfilePic().getBytes(1, blobLength);
        ByteArrayResource data = new ByteArrayResource(byteArray);

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(blobLength)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(data);
    }

    /**
     * Retrieves user's profile picture
     * @param user Username of User
     * @return Blob representing profile picture
     */
    @ApiOperation(value = "Retrieves profile picture", response = Blob.class)
    @GetMapping(path = "/user/{user}/pic/blob")
    public Blob getUserProfilePicBlob(@PathVariable String user) {
        return userRepository.findByUsername(user).getProfilePic();
    }

    @ApiOperation(value = "Updates user's profile picture", response = String.class)
    @PutMapping(path = "/user/{user}/pic/new2")
    public String setUserProfilePic(@PathVariable String user, @RequestBody MultipartFile profilePic) throws Exception {
        User requestedUser = userRepository.findByUsername(user);
        
        if (requestedUser == null || profilePic == null) {
            return failure;
        } 
        
        requestedUser.setExtension(profilePic.getOriginalFilename());

        userRepository.save(requestedUser);

        try { 
            byte[] file = profilePic.getBytes();
            SerialBlob blob = new SerialBlob(file);
            Blob image = blob;
            requestedUser.setProfilePic(image);
            userRepository.save(requestedUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }  

    @PutMapping(path = "/user/{user}/pic/new")
    public String setUserProfilePicBytes(@PathVariable String user, @RequestParam String file) {
        User requestedUser = userRepository.findByUsername(user);

        if (requestedUser == null || file == null) {
            return failure;
        }

        byte[] bytes = Base64.getDecoder().decode(file);

        requestedUser.setExtension(user + "-profile-picture.png");

        userRepository.save(requestedUser);

        try {
            SerialBlob blob = new SerialBlob(bytes);
            Blob image = blob;
            requestedUser.setProfilePic(image);
            userRepository.save(requestedUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    /**
     * Retrieves filename and extension of User's profile picture
     * @param user Username of user
     * @return String representing filename and extension of profile picture
     */
    @ApiOperation(value = "Retrieves filename and extension of profile picture", response = String.class)
    @GetMapping(path = "/user/{user}/extension")
    public String getUserExtension(@PathVariable String user) {
        User requestedUser = userRepository.findByUsername(user);

        return requestedUser.getExtension();
    }  

    /**
     * Retrieves privilege level of User as String
     * @param user Username of user
     * @return String representing user's privilege level
     */
    @ApiOperation(value = "Retrieves user's privilege level", response = String.class)
    @GetMapping(path = "/user/{user}/privilege")
    public int getUserPrivilegeLevel(@PathVariable String user) {
        User requestedUser = userRepository.findByUsername(user);

        return requestedUser.getPLevel();
    }

    @PutMapping(path = "/user/{user}/privilege/new")
    public String updateUserPLevel(@PathVariable String user, @RequestBody ObjectNode json) {
        User requestedUser = userRepository.findByUsername(user);
        String input = json.get("pLevel").textValue();
        if (input == null) {
        	return failure;
        }
        
        int pLevel = Integer.valueOf(input);

        if (pLevel >= 3 && pLevel <= 0) {
        	return failure;
        }
        if (requestedUser == null) {
            return failure;
        }

        requestedUser.setPLevel(pLevel);
        userRepository.save(requestedUser);
        return success;
    }

}