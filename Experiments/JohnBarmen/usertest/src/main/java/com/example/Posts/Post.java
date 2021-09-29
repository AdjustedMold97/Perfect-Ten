package com.example.Posts;

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

    /* 
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private String message;
    

    /*
     * @ManyToOne tells springboot that multiple instances of Phone can map to one instance of OR multiple rows of the phone table can map to one user row
     * @JoinColumn specifies the ownership of the key i.e. The Phone table will contain a foreign key from the User table and the column name will be user_id
     * @JsonIgnore is to assure that there is no infinite loop while returning either user/phone objects (phone->user->[phones]->...)
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

     // =============================== Constructors ================================== //

    public Post(){

    }
    
    public Post( String message) {
        this.message = message;
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


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}


