package com.sparta.project01.controller;


import com.sparta.project01.domain.Post;
import com.sparta.project01.domain.PostRepository;
import com.sparta.project01.domain.PostRequestDto;
import com.sparta.project01.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostRepository postRepository;
    private final PostService postService;


    @PostMapping("/api/posts")
    public Post createPost(@RequestBody PostRequestDto requestDto){
        Post post=new Post(requestDto);
        return postRepository.save(post);
    }

    @GetMapping("/api/posts")
    public List<Post> readPosts(){
        return postRepository.findAllByOrderByModifiedAtDesc();

    }

    @GetMapping("/api/posts/{id}")
    public Optional<Post> readPost(@PathVariable Long id){

        return postRepository.findById(id);
    }

    @DeleteMapping("/api/posts/{id}")
    public Long deletePost(@PathVariable Long id){
        postRepository.deleteById(id);
        return id;
    }

    @PutMapping("/api/posts/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto){
        return postService.update(id, requestDto);
    }
}
