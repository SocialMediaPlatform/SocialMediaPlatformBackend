package com.social_media_platform.social_media_platform_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.social_media_platform.social_media_platform_backend.databaseTables.Post;
import com.social_media_platform.social_media_platform_backend.databaseTables.User;
import com.social_media_platform.social_media_platform_backend.databaseTables.Reaction;
import com.social_media_platform.social_media_platform_backend.repositiries.CommentRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.PostRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.ReactionRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.UserRepository;

@Service
public class PostService {
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final ReactionRepository reactionRepository;
  private final CommentRepository commentRepository;

  public PostService(
      PostRepository postRepository,
      UserRepository userRepository,
      ReactionRepository reactionRepository,
      CommentRepository commentRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.reactionRepository = reactionRepository;
    this.commentRepository = commentRepository;
  }

  public List<Post> getUserPosts(Long userId) {
    return postRepository.getUserPosts(userId);
  }

  public void createPost(Long userId, Post post) throws Exception {
    post.setUser(
        userRepository.findById(userId).orElseThrow(() -> new Exception("User not found")));
    postRepository.save(post);
  }

  public List<Post> getUsersPosts(List<User> userIds) {
    return postRepository.getUsersPosts(userIds);
  }

  public List<Reaction> getPostReactions(Long postId) {
    return reactionRepository.getPostReactions(postId);
  }

  public int getPostReactionsCount(Long postId) {
    return reactionRepository.getPostReactionsCount(postId);
  }

  public int getPostCommentsCount(Long postId) {
    return commentRepository.getPostCommentsCount(postId);
  }
}
