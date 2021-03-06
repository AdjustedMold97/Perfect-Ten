package com.example.Users;

import java.util.ArrayList;
import java.util.List;
import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.example.Posts.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.mysql.cj.jdbc.Blob;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * This class contains all of the information stored for a User entity
 * @author Adam Sweiger
 */
@Entity
public class User {

    /**
     * Enumeration of User privilege levels. Base Users can create posts, send DMs, and add friends.
     * Moderators can do everything users can as well as delete posts and ban users. 
     * Admins can do everything moderators can as well as promote a base user to moderator status.
     */
    /*public static enum PrivilegeLevel {
        USER,
        MODERATOR,
        ADMIN
    }*/

    /**
     * Each User has a unique ID
     */
    @ApiModelProperty(notes = "ID of the User", name = "id", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Each User has a unique username
     */
    @ApiModelProperty(notes = "Username of the User", name = "username", required = true)
    private String username;

    /**
     * User's email
     */
    @ApiModelProperty(notes = "Email of the User", name = "email", required = true)
    private String email;

    /**
     * User's password
     */
    @ApiModelProperty(notes = "Password of the User", name = "password", required = true)
    private String password;

    /**
     * Privilege level of User. Either base user (0), moderator (1), or admin (2)
     */
    @ApiModelProperty(notes = "Privilege level of the User", name="pLevel", required = true)
    private int pLevel;

    /**
     * Notification preferences of User. If True, User will receieve notifications
     */
    @ApiModelProperty(notes = "Notification preferences of the User", name = "notifications", required = true)
    private Boolean notifications;

    /**
     * List of posts created by User
     */
    @ApiModelProperty(notes = "Posts list of the User", name = "posts", required = true)
    @OneToMany(
    		fetch = FetchType.LAZY
    		)
    private List<Post> posts;

    /**
     * List of other Users User is friends with
     */
    @ApiModelProperty(notes = "Friends list of the User", name = "friends", required = true)
    @ManyToMany
    @JoinTable(name="friends_with", 
    joinColumns={@JoinColumn(name="user_id")},
    inverseJoinColumns={@JoinColumn(name="friend_id")})
    @JsonIgnore
    private List<User> friends;

    /**
     * List of Users User is blocking
     */
    @ApiModelProperty(notes = "Blocked list of the User", name = "blockedUsers", required = true)
    @ManyToMany
    private List<User> blockedUsers;

    /**
     * String representing filename and extension of profile picture
     */
    @ApiModelProperty(notes = "Filename and extension of profile picture", name = "extension", required = false)
    private String extension;

    /**
     * Profile picture of User stored as Binary Large Object
     */
    @ApiModelProperty(notes = "Profile picture of the User", name = "profilePic", required = false)
    @JsonIgnore
    @Lob
    private Blob profilePic;

    // ======================== Constructors =====================================

    /**
     * Creates a new User with associated username, email, and password. Lists are initially empty.
     * By default, notifications are turned on.
     * @param username Username of User
     * @param email Email of User
     * @param password Password of User
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        pLevel = 0;
        notifications = true;
        posts = new ArrayList<>();
        friends = new ArrayList<>();
        blockedUsers = new ArrayList<>();
    }

    /**
     * Creates a new User with associated usernmae, email, password, and pLevel. 
     * Lists are initially empty. By default, notifications are turned on.
     * @param username Username of User
     * @param email Email of User
     * @param password Password of User
     * @param pLevel Privilege level of User. Can be USER, MODERATOR, or ADMIN
     */
    public User(String username, String email, String password, int pLevel) {
        this.username = username;
        this.email = email;
        this.password = password;
        pLevel = this.pLevel;
        notifications = true;
        posts = new ArrayList<>();
        friends = new ArrayList<>();
        blockedUsers = new ArrayList<>();
    }

    /**
     * Creates a new User with empty posts, friends, and blocked lists
     */
    public User() {
    	pLevel = 0;
        notifications = true;
        posts = new ArrayList<>();
        friends = new ArrayList<>();
        blockedUsers = new ArrayList<>();
    }

    // ========================== Accessors and Mutators ==============================

    /**
     * Gets User's ID
     * @return int representing id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets User's ID to id
     * @param id New ID of User
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets username of User
     * @return String representing username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets User's username to username
     * @param username New username of User
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets User's email
     * @return String representing email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets User's email to email
     * @param email New email of User
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets User's password
     * @return String representing password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets User's password to password
     * @param password New password of User
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets User's privilege level
     * @return USER (0), MODERATOR (1), or ADMIN (2)
     */
    public int getPLevel() {
        return pLevel;
    }

    /**
     * Sets User's privilege level to pLevel
     * @param pLevel New pLevel, either USER (0), MODERATOR (1), or ADMIN (2)
     */
    public void setPLevel(int pLevel) {
        this.pLevel = pLevel;
    }

    /**
     * Gets User's notification preferences
     * @return True if User wants to receive notifications, false otherwise
     */
    public Boolean getNotificationPref() {
        return notifications;
    }

    /**
     * Sets User's notifications preferences to notifications
     * @param notifications Updated notification preferences of User
     */
    public void setNotificationPref(Boolean notifications) {
        this.notifications = notifications;
    }

    /**
     * Gets User's list of posts
     * @return List representing posts created by User
     */
    public List<Post> getPosts() {
        return posts;
    }

    /**
     * Sets User's posts list to posts
     * @param posts New posts list of User
     */
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    /**
     * Adds post to User's posts list
     * @param post Post to be added to User's posts list
     */
    public void addPost(Post post) {
        this.posts.add(post);
    }

    public void removePost(Post post) {
        this.posts.remove(post);
    }

    /**
     * Gets User's friends list
     * @return List representing users that User is friends with
     */
    public List<User> getFriends() {
        return friends;
    }

    /**
     * Sets User's friends list to friends
     * @param friends New friends list of User
     */
    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    /**
     * Adds new friend to User's friends list
     * @param friend New friend to be added to friends list
     */
    public void addFriend(User friend) {
        this.friends.add(friend);
    }  

    /**
     * Removes a user from User's friends list
     * @param friend User to be removed from friends list
     */
    public void removeFriend(User friend) {
        this.friends.remove(friend);
    }

    /**
     * Checks if friend is in User's friends list
     * @param friend User to be checked
     * @return True if friend is in friends list, False otherwise
     */
    public Boolean isFriendsWith(User friend) {
        return friends.contains(friend);
    }

    /**
     * Gets User's blocked list
     * @return List representing users that User is blocking
     */
    public List<User> getBlockedUsers() {
        return blockedUsers;
    }

    /**
     * Sets User's blocked list to blockedUsers
     * @param blockedUsers New list of Users to be blocked
     */
    public void setBlockedUsers(List<User> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    /**
     * Adds a new user to User's blocked list
     * @param blockedUser user to be added to User's blocked list
     */
    public void addBlockedUser(User blockedUser) {
        this.blockedUsers.add(blockedUser);
    }

    /**
     * Removes a user from User's blocked list
     * @param blockedUser user to be removed from User's blocked list
     */
    public void removeBlockedUser(User blockedUser) {
        this.blockedUsers.remove(blockedUser);
    }

    /**
     * Checks if User is blocking blockedUser
     * @param blockedUser user that User is potentially blocking
     * @return True if blockedUser is in User's blocked list, False otherwise
     */
    public Boolean isBlocking(User blockedUser) {
        return blockedUsers.contains(blockedUser);
    }

    /**
     * Gets extension of User's profile picture
     * @return String representing extension of profile picture
     */
    public String getExtension() {
        return extension;
    }
    
    /**
     * Sets User's profile picture extension to extension
     * @param extension New filename + extension of profile picture
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * Gets profile picture of User
     * @return Binary Large Object (Blob) representing User's profile picture
     */
    public Blob getProfilePic() {
        return profilePic;
    }

    /**
     * Sets User's profile picture to profilePic
     * @param profilePic Blob representing User's new profile picture
     */
    public void setProfilePic(Blob profilePic) {
        this.profilePic = profilePic;
    }
}