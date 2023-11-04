package com.example.sociospring.service;

import com.example.sociospring.Response.ResponseMessage;
import com.example.sociospring.models.Follower;
import com.example.sociospring.models.User;
import com.example.sociospring.repository.FollowerRepository;
import com.example.sociospring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

@Service

public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FollowerRepository followerRepository;


    public User createUser(User user){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(currentDate);
        user.setCreatedAt(formattedDate);
        return userRepository.save(user);
    }
    public ResponseEntity<Object> getUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        return ResponseEntity.ok().body(user);
    }
    public ResponseEntity<ResponseMessage> updateUser(Long id, User userDetails) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            User existingUser = user.get();
            if (userDetails.getUserName() != null) {
                existingUser.setUserName(userDetails.getUserName());
            }
            if (userDetails.getBio() != null) {
                existingUser.setBio(userDetails.getBio());
            }
            if (userDetails.getEmail() != null) {
                existingUser.setEmail(userDetails.getEmail());
            }
            if (userDetails.getProfilePictureUrl() != null) {
                existingUser.setProfilePictureUrl(userDetails.getProfilePictureUrl());
            }
            if (userDetails.getHashPassword() != null) {
                existingUser.setHashPassword(userDetails.getHashPassword());
            }
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(currentDate);
            existingUser.setUpdatedAt(formattedDate);
            userRepository.save(existingUser);
            return ResponseEntity.ok().body(new ResponseMessage("User with ID " + id + " updated succesfuly"));
        }else{
            return ResponseEntity.ok().body(new ResponseMessage("User with ID " + id + " does not exist"));
        }
    }

    public ResponseEntity<Object> deleteAllUsers() {
        try {
            userRepository.deleteAll();
            String successMessage = "All Users have been successfully deleted";
            return new ResponseEntity<>(successMessage, HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = "Failed to delete user with ID ";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<ResponseMessage> deleteUserById(Long id) {
        try {
            Optional<User> userExist = userRepository.findById(id);
            if(userExist.isPresent()){
                userRepository.deleteById(id);
                String successMessage = "User with ID " + id + " has been successfully deleted";
                return ResponseEntity.ok(new ResponseMessage(successMessage));
            }else{
                return ResponseEntity.ok(new ResponseMessage("User with ID " + id + " does not exist"));

            }
            } catch (Exception e) {
            String errorMessage = "Failed to delete user with ID " + id;
            return ResponseEntity.ok(new ResponseMessage(errorMessage));
        }
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public List<Follower> getFollowersOfUser(Long userId){
        return followerRepository.findByUserId(userId);
    }
//    public List<Follower> getFollowingOfUser(Long userId){
//        return followerRepository.followingFindByUser_Id(userId);
//    }

    public ResponseEntity<ResponseMessage> followUser(Follower followerRequest){
        try {
            List<Follower> existingFollower = followerRepository.findByUserId(followerRequest.getUserId());
            if(existingFollower.isEmpty()){
                Follower follower = new Follower();
                follower.setFollowerId(followerRequest.getFollowerId());
                follower.setUserId(followerRequest.getUserId());
                followerRepository.save(follower);
                return ResponseEntity.ok(new ResponseMessage("Followed User!"));
            }else{
                return ResponseEntity.ok(new ResponseMessage("Already Followed by User!"));
            }
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while posting a comment", ex);
        }
    }

    public ResponseEntity<ResponseMessage> unfollowUser(Follower followerRequest){
        try {
            Optional<Follower> existingFollower = followerRepository.findById(followerRequest.getId());
            if(existingFollower.isPresent()){
                followerRepository.deleteById(followerRequest.getId());
                return ResponseEntity.ok(new ResponseMessage("UnFollowed by User!"));
            }else{
                return ResponseEntity.ok(new ResponseMessage("UnFollowed User Already!"));
            }
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while posting a comment", ex);
        }
    }
}
