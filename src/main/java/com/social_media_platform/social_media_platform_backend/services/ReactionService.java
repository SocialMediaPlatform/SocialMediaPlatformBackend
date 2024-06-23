package com.social_media_platform.social_media_platform_backend.services;

import org.springframework.stereotype.Service;

import com.social_media_platform.social_media_platform_backend.databaseTables.Comment;
import com.social_media_platform.social_media_platform_backend.databaseTables.Post;
import com.social_media_platform.social_media_platform_backend.databaseTables.Reaction;
import com.social_media_platform.social_media_platform_backend.databaseTables.ReactionType;
import com.social_media_platform.social_media_platform_backend.databaseTables.User;
import com.social_media_platform.social_media_platform_backend.repositiries.CommentRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.PostRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.ReactionRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.ReactionTypeRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.UserRepository;

@Service
public class ReactionService {
  private final ReactionRepository reactionRepository;
  private final PostRepository postRepository;
  private final ReactionTypeRepository reactionTypeRepository;
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;

  public ReactionService(
      ReactionRepository reactionRepository,
      PostRepository postRepository,
      ReactionTypeRepository reactionTypeRepository,
      CommentRepository commentRepository,
      UserRepository userRepository) {
    this.reactionRepository = reactionRepository;
    this.postRepository = postRepository;
    this.reactionTypeRepository = reactionTypeRepository;
    this.commentRepository = commentRepository;
    this.userRepository = userRepository;
  }

  public void addPostReaction(Long postId, Long reactionTypeId, Long userId) throws Exception {
    User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
    Post post = postRepository.findById(postId).orElseThrow(() -> new Exception("Post not found"));
    ReactionType reactionType =
        reactionTypeRepository
            .findById(reactionTypeId)
            .orElseThrow(() -> new Exception("Reaction type not found"));
    reactionRepository.save(new Reaction(post, reactionType, user));
  }

  public void addCommentReaction(Long commentId, Long reactionTypeId, Long userId)
      throws Exception {
    User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
    Comment comment =
        commentRepository.findById(commentId).orElseThrow(() -> new Exception("Comment not found"));
    ReactionType reactionType =
        reactionTypeRepository
            .findById(reactionTypeId)
            .orElseThrow(() -> new Exception("Reaction type not found"));
    reactionRepository.save(new Reaction(comment, reactionType, user));
  }

  public void removePostReaction(Long postId, Long userId) throws Exception {
    userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
    postRepository.findById(postId).orElseThrow(() -> new Exception("Post not found"));
    Reaction reaction =
        reactionRepository.findByPostIdAndUserId(postId, userId).stream().findFirst().orElse(null);
    if (reaction == null) {
      throw new Exception("Reaction not found");
    }
    reactionRepository.delete(reaction);
  }

  public void removeCommentReaction(Long commentId, Long userId) throws Exception {
    userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
    commentRepository.findById(commentId).orElseThrow(() -> new Exception("Comment not found"));
    Reaction reaction =
        reactionRepository.findByCommentIdAndUserId(commentId, userId).stream()
            .findFirst()
            .orElse(null);
    if (reaction == null) {
      throw new Exception("Reaction not found");
    }
    reactionRepository.delete(reaction);
  }
}
