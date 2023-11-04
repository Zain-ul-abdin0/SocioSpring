package com.example.sociospring.repository;

import com.example.sociospring.models.Follower;
import com.example.sociospring.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    List<Follower> findByUserId(Long userId);
//    List<Follower> findByUser_Id(Long userId);

}
