package com.social_media_platform.social_media_platform_backend.services;

import org.springframework.stereotype.Service;

import com.social_media_platform.social_media_platform_backend.repositiries.PostRepository;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
}
