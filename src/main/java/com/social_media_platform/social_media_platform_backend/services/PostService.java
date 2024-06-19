package com.social_media_platform.social_media_platform_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.social_media_platform.social_media_platform_backend.databaseTables.Post;
import com.social_media_platform.social_media_platform_backend.repositiries.PostRepository;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getUserPosts(Long userId) {
        return postRepository.getUserPosts(userId);
    }
}
