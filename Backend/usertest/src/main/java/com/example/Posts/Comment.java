package com.example.Posts;
import com.example.Users.*;

import io.swagger.annotations.*;
/**
 * @author John Barmen
 */

public class Comment {
	@ApiModelProperty(notes = "Message",name="mess",required=true,value="message")
	private String mess;
	@ApiModelProperty(notes = "User",name="user",required=true,value="user")
	private User user;
	
	/**
	 * Creates a new comment with message m and user u
	 * @param m
	 * @param u
	 */
	public Comment(String m, User u) {
		mess = m;
		user = u;
	}
	
	/**
	 * gets the message
	 * @return message
	 */
	public String getMessage() {
		return mess;
	}
	
	/**
	 * sets the message to input message
	 * @param m
	 */
	public void setMessage(String m) {
		mess = m;
	}
	
	/**
	 * gets the user
	 * @return
	 */
	public User getUser() {
		return user;
	}
}
