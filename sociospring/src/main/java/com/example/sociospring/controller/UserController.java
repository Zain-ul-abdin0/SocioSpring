package com.example.sociospring.controller;

import com.example.sociospring.Response.ResponseMessage;
import com.example.sociospring.models.Follower;
import com.example.sociospring.models.User;
import com.example.sociospring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteUser(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }

    @DeleteMapping()
    public ResponseEntity<Object> deleteAllUsers() {
        return userService.deleteAllUsers();
    }

    @GetMapping("/followers/{id}")
    public List<Follower> getFollowersOfUser(@PathVariable Long userId) {
        return userService.getFollowersOfUser(userId);
    }

    @GetMapping("/following/{id}")
    public List<Follower> getFollowingOfUser(@PathVariable Long userId) {
        return userService.getFollowersOfUser(userId);
    }

    @PostMapping("/follow")
    public ResponseEntity<ResponseMessage> followUser(@RequestBody Follower follower) {
        return userService.followUser(follower);
    }

    @PostMapping("/unfollow")
    public ResponseEntity<ResponseMessage> unfollowUser(@RequestBody Follower follower) {
        return userService.unfollowUser(follower);
    }

}