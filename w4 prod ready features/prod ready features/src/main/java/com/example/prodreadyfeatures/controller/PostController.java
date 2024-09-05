package com.example.prodreadyfeatures.controller;

import com.example.prodreadyfeatures.dto.PostDto;
import com.example.prodreadyfeatures.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    public PostDto getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping
    public PostDto createPost(@RequestBody PostDto postDto) {
        return postService.createNewPost(postDto);
    }
}
