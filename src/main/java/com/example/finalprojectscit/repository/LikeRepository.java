package com.example.finalprojectscit.repository;

import com.example.finalprojectscit.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
}
