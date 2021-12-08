package com.example.Posts;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.web.multipart.MultipartFile;

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
    
    private String extension;
    
    @ApiModelProperty(notes = "picture assosiated with the post",name="media",required=true,value="media")
    @JsonIgnore
    @Lob
    private Blob media;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @ApiModelProperty(notes = "User assosiated with the post",name="user",required=true,value="user")
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    @ApiModelProperty(notes = "Child Posts of this post", name="children", required=true, value="children")
    private List<Post> children;

    @ApiModelProperty(notes = "True if this post is a child of another", name ="isAChild", required = true, value="isAChild")
    private Boolean isAChild;

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
        children = new ArrayList<Post>();
    }
    
    /**
     * creates a post with message of the message param
     * @param message
     */
    public Post(String message) {
    	this.message = message;
    	title = "default";
        children = new ArrayList<Post>();
    }

    /**
     * the most useful constructor that makes a post with message, title and user set to the prams
     * @param message
     * @param title
     * @param media
     */
    public Post(String message, String title, MultipartFile media) {
        this.message = message;
        this.title = title;
        children = new ArrayList<Post>();
        
        this.extension = media.getOriginalFilename();
        try { 
            byte[] file = media.getBytes();
            SerialBlob blob = new SerialBlob(file);
            Blob image = blob;
            this.media = image;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * constructor
     * @param message
     * @param title
     * @param user
     */
    public Post(String message, String title, User user) {
    	this.message = message;
    	this.title = title;
    	this.user = user;
    	uname = user.getUsername();
        children = new ArrayList<Post>();
    }
    
    /**
     * constuctor
     * @param message
     * @param title
     * @param user
     * @param media
     */
    public Post(String message, String title, User user, MultipartFile media) {
    	this.message = message;
    	this.title = title;
    	this.user = user;
    	uname = user.getUsername();
        children = new ArrayList<Post>();
    	
    	 this.extension = media.getOriginalFilename();
         try { 
             byte[] file = media.getBytes();
             SerialBlob blob = new SerialBlob(file);
             Blob image = blob;
             this.media = image;
         } catch (Exception e) {
             e.printStackTrace();
         }
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
    
    /**
     * returns media assosiated with the post
     * @return
     */
    public Blob getMedia() {
    	return media;
    }
    
    /**
     * sets the media assosiated with the post
     * @param in
     */
    public void setMedia(Blob in) {
    	media = in;
    }
    
    public String getExtension() {
    	return extension;
    }
    
    public void setExtension(String in) {
    	extension = in;
    }
    
    public List<Post> getChildren() {
        return children;
    }

    public void setChildren(List<Post> children) {
        this.children = children;
    }

    public void addChild(Post child) {
        children.add(child);
    }

    public void removeChild(Post child) {
        children.remove(child);
    }

    public Boolean getIsAChild() {
        return isAChild;
    }

    public void setIsAChild(Boolean isAChild) {
        this.isAChild = isAChild;
    }
    
}


