package com.example.sociospring.models;

import jakarta.persistence.*;

@Entity
@Table(name = "follower")
public class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long followerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public Follower(Long id, Long userId, Long followerId) {
        this.id = id;
        this.userId = userId;
        this.followerId = followerId;
    }

    public Follower() {
    }

    @Override
    public String toString() {
        return "Follower{" +
                "id=" + id +
                ", userId=" + userId +
                ", followerId=" + followerId +
                '}';
    }
}
