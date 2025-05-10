package com.alkemy.java2.repository;

import com.alkemy.java2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    // Add this custom exists method
    default boolean existsByUsername(String username) {
        return findByUsername(username).isPresent();
    }
}