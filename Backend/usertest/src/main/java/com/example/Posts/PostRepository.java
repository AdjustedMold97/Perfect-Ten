package com.example.Posts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Posts.*;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findById(int id);
}
