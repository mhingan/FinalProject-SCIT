package com.example.finalprojectscit.repository;

import com.example.finalprojectscit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<Object> findByEmail(String username);

    Object findAllActive();
}
