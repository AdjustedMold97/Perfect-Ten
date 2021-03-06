package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.example.Users.User;
import com.example.Users.UserRepository;
import com.example.Posts.Post;
import com.example.Posts.PostRepository;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	CommandLineRunner initUser(UserRepository userRepository, PostRepository postRepository) {
		return args -> {
			User user1 = new User("Bob", "bob123@gmail.com", "password1");
			User user2 = new User("Tom", "tom123@test.com", "password2");
			User user3 = new User("Jeff", "jeff123@test.com", "password3");
			userRepository.save(user1);
			userRepository.save(user2);
			userRepository.save(user3);
		};
	}

}
