package com.example.Users;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(int id);
    @Transactional
    void deleteById(int id);
    User findByUsername(String username);
}
