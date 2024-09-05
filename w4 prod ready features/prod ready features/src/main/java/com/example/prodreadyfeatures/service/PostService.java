package com.example.prodreadyfeatures.service;

import com.example.prodreadyfeatures.dto.PostDto;

import java.util.List;

public interface PostService {


    List<PostDto> getAllPosts();

    PostDto createNewPost(PostDto postDto);

    PostDto getPostById(Long postId);
}
