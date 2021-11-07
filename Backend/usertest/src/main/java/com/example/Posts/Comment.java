package com.example.Posts;
import com.example.Users.*;

import io.swagger.annotations.*;

public class Comment {
	@ApiModelProperty(notes = "Message",name="mess",required=true,value="message")
	private String mess;
	@ApiModelProperty(notes = "User",name="user",required=true,value="user")
	private User user;
	
	
	public Comment(String m, User u) {
		mess = m;
		user = u;
	}
	
	public String getMessage() {
		return mess;
	}
	
	public void setMessage(String m) {
		mess = m;
	}
	
	public User getUser() {
		return user;
	}
}
