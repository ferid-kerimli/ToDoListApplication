package com.example.todolistapplication.repository;

import com.example.todolistapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameOp(String username);
    Optional<User> findByEmail(String email);
    User findByUsername(String username);
}
