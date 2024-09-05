package com.example.prodreadyfeatures.service.impl;

import com.example.prodreadyfeatures.dto.PostDto;
import com.example.prodreadyfeatures.entity.Post;
import com.example.prodreadyfeatures.exception.ResourceNotFoundException;
import com.example.prodreadyfeatures.respository.PostRepository;
import com.example.prodreadyfeatures.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> modelMapper.map(post, PostDto.class)).toList();
    }

    @Override
    public PostDto createNewPost(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto getPostById(Long postId) {
        Post post = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        return modelMapper.map(post, PostDto.class);
    }
}
