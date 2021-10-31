package com.example.Users;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.example.Posts.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private String password;

    @OneToMany(
    		fetch = FetchType.LAZY
    		)
    
    private List<Post> posts;

    @ManyToMany
    @JoinTable(name="friends_with", 
    joinColumns={@JoinColumn(name="user_id")},
    inverseJoinColumns={@JoinColumn(name="friend_id")})
    @JsonIgnore
    private List<User> friends;

    @ManyToMany
    private List<User> blockedUsers;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        posts = new ArrayList<>();
        friends = new ArrayList<>();
        blockedUsers = new ArrayList<>();
    }

    public User() {
        posts = new ArrayList<>();
        friends = new ArrayList<>();
        blockedUsers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public void addFriend(User friend) {
        this.friends.add(friend);
    }

    public void removeFriend(User friend) {
        this.friends.remove(friend);
    }

    public Boolean isFriendsWith(User friend) {
        return friends.contains(friend);
    }

    public List<User> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(List<User> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public void addBlockedUser(User blockedUser) {
        this.blockedUsers.add(blockedUser);
    }

    public void removeBlockedUser(User blockedUser) {
        this.blockedUsers.remove(blockedUser);
    }

    public Boolean isBlocking(User blockedUser) {
        return blockedUsers.contains(blockedUser);
    }
}
