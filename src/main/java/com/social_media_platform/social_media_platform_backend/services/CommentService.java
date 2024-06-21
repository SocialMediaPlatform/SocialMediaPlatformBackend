package com.social_media_platform.social_media_platform_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.social_media_platform.social_media_platform_backend.repositiries.CommentRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.PostRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.UserRepository;
import com.social_media_platform.social_media_platform_backend.controllers.requests.AddMainCommentRequest;
import com.social_media_platform.social_media_platform_backend.databaseTables.Comment;
import com.social_media_platform.social_media_platform_backend.databaseTables.MainComment;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository,
            UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Comment> getCommentsInPost(Long postId) throws Exception {
        postRepository.findById(postId).orElseThrow(() -> new Exception("Post not found"));
        return commentRepository.findByPostId(postId);
    }

    public void addComment(AddMainCommentRequest addMainCommentRequest) throws Exception {
        var post = postRepository.findById(addMainCommentRequest.getPostId())
                .orElseThrow(() -> new Exception("Post not found"));
        var user = userRepository.findById(addMainCommentRequest.getUserId())
                .orElseThrow(() -> new Exception("User not found"));

        var mainComment = new MainComment();
        mainComment.setCommentContents(addMainCommentRequest.getCommentContents());
        mainComment.setCommentDate(addMainCommentRequest.getCommentDate());
        mainComment.setPost(post);
        mainComment.setUser(user);

        commentRepository.save(mainComment);
    }
}
