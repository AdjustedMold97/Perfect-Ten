package com.example.Posts;
import com.example.Users.*;

public class Comment {
	private String mess;
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
