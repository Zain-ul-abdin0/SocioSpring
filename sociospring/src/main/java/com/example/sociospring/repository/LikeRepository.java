package com.example.sociospring.repository;

import com.example.sociospring.models.Follower;
import com.example.sociospring.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like>  findByUserId(Long userId);

}
