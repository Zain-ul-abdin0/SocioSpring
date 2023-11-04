package com.example.sociospring.repository;

import com.example.sociospring.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment,Long> {

}
