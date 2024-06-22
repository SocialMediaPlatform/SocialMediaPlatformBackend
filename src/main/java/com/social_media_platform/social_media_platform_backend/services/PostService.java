package com.social_media_platform.social_media_platform_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.social_media_platform.social_media_platform_backend.databaseTables.Post;
import com.social_media_platform.social_media_platform_backend.databaseTables.Reaction;
import com.social_media_platform.social_media_platform_backend.repositiries.PostRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.ReactionRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.UserRepository;

@Service
public class PostService {
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final ReactionRepository reactionRepository;

  public PostService(PostRepository postRepository, UserRepository userRepository,
      ReactionRepository reactionRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.reactionRepository = reactionRepository;
  }

  public List<Post> getUserPosts(Long userId) {
    return postRepository.getUserPosts(userId);
  }

  public void createPost(Long userId, Post post) throws Exception {
    post.setUser(
        userRepository.findById(userId).orElseThrow(() -> new Exception("User not found")));
    postRepository.save(post);
  }

  public List<Reaction> getPostReactions(Long postId) {
    return reactionRepository.getPostReactions(postId);
  }
}
