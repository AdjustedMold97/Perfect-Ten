package com.example.Posts;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.example.Users.*;

@Entity
public class Post {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private String message;
    private String title;
    private String uname;
    
    @JsonIgnore
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

     // =============================== Constructors ================================== //

    public Post(){

    }
    
    public Post(String message, String title) {
        this.message = message;
        this.title = title;
    }
    
    public Post(String message) {
    	this.message = message;
    	title = "default";
    }

    public Post(String message, String title, User user) {
        this.message = message;
        this.title = title;
        this.user = user;
        uname = user.getUsername();
    }

    // =============================== Getters and Setters for each field ================================== //


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTitle(String title) {
    	this.title = title;
    }
    
    public String getTitle() {
    	return title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment(int id) {
    	if(id >= comments.size()) {
    		return "{\"message\":\"error1\"}";
    	}
    	else {
    		String temp = new String();
    		temp = "{\"user\":\"";
    		temp = temp.concat(comments.get(id).getUser().getUsername());
    		temp = temp.concat(",\"message\":\"");
    		temp = temp.concat(comments.get(id).getMessage());
    		temp = temp.concat("\"}");
    		return temp;
    	}
    }
    
    public void delComment(int id) {
    	if(id < comments.size()) {
    		comments.remove(id);
    	}
    }
    
    public void addComment(String m, User u) {
    	comments.add(new Comment(m,u));
    }
}


