package com.social_media_platform.social_media_platform_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.social_media_platform.social_media_platform_backend.repositiries.CommentRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.PostRepository;

import com.social_media_platform.social_media_platform_backend.databaseTables.Comment;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<Comment> getCommentsInPost(Long postId) throws Exception {
        postRepository.findById(postId).orElseThrow(() -> new Exception("Post not found"));
        return commentRepository.findByPostId(postId);
    }
}
