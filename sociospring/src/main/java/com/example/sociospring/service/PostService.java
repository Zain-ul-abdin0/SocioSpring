package com.example.sociospring.service;

import com.example.sociospring.Response.ResponseMessage;
import com.example.sociospring.models.Comment;
import com.example.sociospring.models.Like;
import com.example.sociospring.models.Post;
import com.example.sociospring.models.User;
import com.example.sociospring.repository.CommentRepository;
import com.example.sociospring.repository.LikeRepository;
import com.example.sociospring.repository.PostRepository;
import com.example.sociospring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    public Post createPost(Post post){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(currentDate);
        post.setCreatedAt(formattedDate);

        // Retrieve the User entity by ID (assuming you have a userRepository)
        User user = userRepository.findById(post.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Set the User in the Post entity
        post.setUser(user);

        return postRepository.save(post);
    }
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }
    public Optional<Post> getPostById(Long id){
        return postRepository.findById(id);
    }
    public ResponseEntity<Object> deletePostById(Long id){
        try {
            Optional<Post> existingPost = postRepository.findById(id);
            if(existingPost.isPresent()){
                postRepository.deleteById(id);
                String successMessage = "Post with ID " + id + " has been successfully deleted";
                return new ResponseEntity<>(successMessage, HttpStatus.OK);
            }
            else{
                String errorMessage = "Failed to delete user with ID " + id;
                return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }catch (Exception e){
            String errorMessage = "Failed to delete user with ID " + id;
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public Post updatePost(Long id, Post postDeatails){
        Optional<Post> post = postRepository.findById(id);
        if(post.isPresent()){
            Post existingPost = post.get();
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(currentDate);

            existingPost.setContent(postDeatails.getContent());
            existingPost.setUser(postDeatails.getUser());
            existingPost.setPrivacy(postDeatails.getPrivacy());
            existingPost.setUpdatedAt(formattedDate);
            return postRepository.save(existingPost);
        }
        return null;
    }
    public ResponseEntity<ResponseMessage> likePost(Like likeRequest){
        Like likePost = new Like();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(currentDate);
        Optional<Like> likeCheck = likeRepository.findByUserId(likeRequest.getUserId());
        if(likeCheck.isEmpty()){
            likePost.setUserId(likeRequest.getUserId());
            likePost.setPostId(likeRequest.getPostId());
            likePost.setCreatedAt(formattedDate);
            likeRepository.save(likePost);
            return ResponseEntity.ok(new ResponseMessage("Like Saved"));
        }else{
            return ResponseEntity.ok(new ResponseMessage("Already Liked"));
        }
    }
    public ResponseEntity<ResponseMessage> unlikePost(Like unlikeRequest){
        Optional<Like> likeCheck = likeRepository.findByUserId(unlikeRequest.getUserId());
        if(likeCheck.isEmpty()){
            likeRepository.deleteById(unlikeRequest.getId());
            return ResponseEntity.ok(new ResponseMessage("Post has been unliked"));
        }else{
            return ResponseEntity.ok(new ResponseMessage("Post already unliked"));

        }
    }

    public ResponseEntity<ResponseMessage> commentPost(Comment commentRequest){
        try {
            Comment comment = new Comment();
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(currentDate);
            comment.setUserId(commentRequest.getUserId());
            comment.setPostId(commentRequest.getPostId());
            comment.setContent(commentRequest.getContent());
            comment.setCreatedAt(formattedDate);
            commentRepository.save(comment);
            return ResponseEntity.ok(new ResponseMessage("Comment Posted!"));

        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while posting a comment", ex);
        }
        }
    public ResponseEntity<ResponseMessage> deleteComment(Comment requestComment) {
        try {
            Optional<Comment> existingComment = commentRepository.findById(requestComment.getId());
            if (existingComment.isPresent()) {
                commentRepository.deleteById(requestComment.getId());
                return ResponseEntity.ok(new ResponseMessage("Comment deleted successfully"));
            } else {
                return ResponseEntity.ok(new ResponseMessage("Comment Does not exist!"));
            }
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while posting a comment", ex);
        }


    }
}
