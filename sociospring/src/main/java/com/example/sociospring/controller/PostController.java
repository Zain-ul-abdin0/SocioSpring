package com.example.sociospring.controller;

import com.example.sociospring.Response.ResponseMessage;
import com.example.sociospring.models.Comment;
import com.example.sociospring.models.Like;
import com.example.sociospring.models.Post;
import com.example.sociospring.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired(required=false)
    private PostService postService;

    @PostMapping
    public Post createPost(@RequestBody Post post){
        return postService.createPost(post);
    }
    @GetMapping
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }
    @GetMapping("/{id}")
    public Optional<Post> getPostById(@PathVariable Long id){
        return postService.getPostById(id);
    }
    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id,@RequestBody Post postDetails){
        return postService.updatePost(id,postDetails);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePostById(@PathVariable Long id){
        return postService.deletePostById(id);
    }

    @PostMapping("/like")
    public ResponseEntity<ResponseMessage> likePost(@RequestBody Like likeRequest){
        return postService.likePost(likeRequest);
    }
    @PostMapping("/unlike")
    public ResponseEntity<ResponseMessage> unlikePost(@RequestBody Like unlikeRequest){
        return postService.unlikePost(unlikeRequest);
    }
    @PostMapping("/comment")
    public ResponseEntity<ResponseMessage> commentPost(@RequestBody Comment comment){
        return postService.commentPost(comment);
    }
    @PostMapping("/comment/delete")
    public ResponseEntity<ResponseMessage> deleteComment(@RequestBody Comment comment){
        return postService.deleteComment(comment);
    }
}
