package com.example.Posts;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findById(int id);
    @Transactional
    void deleteById(int id);
}
