package com.example.Posts;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.*;

import com.example.Users.*;
/**
 * @author john Barmen
 */
@Entity
public class Post {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @ApiModelProperty(notes = "ID",name="id",required=true,value="id")
    private int id;
    @ApiModelProperty(notes = "Message",name="message",required=true,value="message")
    private String message;
    @ApiModelProperty(notes = "Title",name="title",required=true,value="title")
    private String title;
    @ApiModelProperty(notes = "Username assosiated with the post",name="uname",required=true,value="username")
    private String uname;
    @ApiModelProperty(notes = "time assosiated with the post",name="time",required=true,value="time")
    private LocalDateTime time;
    
    @JsonIgnore
    @ApiModelProperty(notes = "List of comments assosiated with the post",name="comments",required=true,value="comments")
    private List<Comment> comments;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @ApiModelProperty(notes = "User assosiated with the post",name="user",required=true,value="user")
    private User user;

     // =============================== Constructors ================================== //
    /**
     * creates an empty post
     */
    public Post(){

    }
    
    /**
     * Makes a post with message and title set to the params 
     * @param message
     * @param title
     */
    public Post(String message, String title) {
        this.message = message;
        this.title = title;
        comments = new ArrayList<Comment>();
    }
    
    /**
     * creates a post with message of the message param
     * @param message
     */
    public Post(String message) {
    	this.message = message;
    	title = "default";
        comments = new ArrayList<Comment>();
    }

    /**
     * the most useful constructor that makes a post with message, title and user set to the prams
     * @param message
     * @param title
     * @param user
     */
    public Post(String message, String title, User user) {
        this.message = message;
        this.title = title;
        this.user = user;
        uname = user.getUsername();
        comments = new ArrayList<Comment>();
    }

    // =============================== Getters and Setters for each field ================================== //

    /**
     * gets ID
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * sets id to input id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gets the message
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * sets the message to input message
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * sets title to input title
     * @param title
     */
    public void setTitle(String title) {
    	this.title = title;
    }
    
    /**
     * gets the title
     * @return title
     */
    public String getTitle() {
    	return title;
    }

    /**
     * gets the user
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * sets the user to input user
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
        uname = user.getUsername();
    }

    /**
     * returns the assosiated uname
     * @return
     */
    public String getUname() {
    	return uname;
    }
    
    /**
     * sets uname to the input value
     * @param uname
     */
    public void setUname(String uname) {
    	this.uname= uname;
    }
    
    /**
     * returns the time assosiated with the post
     * @return
     */
    public String getTime() {
    	return time.toString();
    }
    
    /**
     * sets the time of the post
     */
    public void setTime() {
    	time = LocalDateTime.now();
    }

    public List<Comment> getComments() {
        return comments;
    }

    /**
     * gets a comment stored at input index
     * @param id
     * @return comment
     */
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
    		temp = temp.concat(",\"time\":\"");
    		temp = temp.concat(comments.get(id).getTime());
    		temp = temp.concat("\"}");
    		return temp;
    	}
    }
    
    /**
     * deletes a comment stored at input index
     * @param id
     */
    public void delComment(int id) {
    	if(id < comments.size()) {
    		comments.remove(id);
    	}
    }
    
    /**
     * creates a new comment with message m and user u
     * @param m
     * @param u
     */
    public void addComment(String m, User u) {
    	comments.add(new Comment(m,u));
    }
}


