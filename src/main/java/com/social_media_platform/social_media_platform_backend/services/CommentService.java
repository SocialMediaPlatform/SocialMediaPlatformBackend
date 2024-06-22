package com.social_media_platform.social_media_platform_backend.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.social_media_platform.social_media_platform_backend.repositiries.CommentRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.PostRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.ReactionRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.UserRepository;
import com.social_media_platform.social_media_platform_backend.controllers.requests.AddMainCommentRequest;
import com.social_media_platform.social_media_platform_backend.controllers.requests.AddSubCommentRequest;
import com.social_media_platform.social_media_platform_backend.databaseTables.Comment;
import com.social_media_platform.social_media_platform_backend.databaseTables.MainComment;
import com.social_media_platform.social_media_platform_backend.databaseTables.Reaction;
import com.social_media_platform.social_media_platform_backend.databaseTables.SubComment;

@Service
public class CommentService {
  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final ReactionRepository reactionRepository;

  public CommentService(
      CommentRepository commentRepository,
      PostRepository postRepository,
      UserRepository userRepository,
      ReactionRepository reactionRepository) {
    this.commentRepository = commentRepository;
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.reactionRepository = reactionRepository;
  }

  public List<Comment> getCommentsInPost(Long postId) throws Exception {
    postRepository.findById(postId).orElseThrow(() -> new Exception("Post not found"));
    return commentRepository.findByPostId(postId);
  }

  public Set<SubComment> getSubComments(Long commentId) throws Exception {
    commentRepository.findById(commentId).orElseThrow(() -> new Exception("Comment not found"));
    Set<SubComment> subComments = new HashSet<SubComment>();
    for (var comment : commentRepository.findByMainCommentId(commentId)) {
      subComments.add((SubComment) comment);
    }
    return subComments;
  }

  public void addComment(AddMainCommentRequest addMainCommentRequest) throws Exception {
    var post =
        postRepository
            .findById(addMainCommentRequest.getPostId())
            .orElseThrow(() -> new Exception("Post not found"));
    var user =
        userRepository
            .findById(addMainCommentRequest.getUserId())
            .orElseThrow(() -> new Exception("User not found"));

    var mainComment = new MainComment();
    mainComment.setCommentContents(addMainCommentRequest.getCommentContents());
    mainComment.setCommentDate(addMainCommentRequest.getCommentDate());
    mainComment.setPost(post);
    mainComment.setUser(user);

    commentRepository.save(mainComment);
  }

  public void addComment(AddSubCommentRequest addMainCommentRequest) throws Exception {
    var mainComment =
        commentRepository
            .findById(addMainCommentRequest.getMainCommentId())
            .orElseThrow(() -> new Exception("Main comment not found"));
    if (!(mainComment instanceof MainComment)) {
      throw new Exception("Main comment not found");
    }
    var user =
        userRepository
            .findById(addMainCommentRequest.getUserId())
            .orElseThrow(() -> new Exception("User not found"));

    var subComment = new SubComment();
    subComment.setCommentContents(addMainCommentRequest.getCommentContents());
    subComment.setCommentDate(addMainCommentRequest.getCommentDate());
    subComment.setMainComment((MainComment) mainComment);
    subComment.setUser(user);

    commentRepository.save(subComment);
  }

  public List<Reaction> getCommentReactions(Long commentId) throws Exception {
    commentRepository.findById(commentId).orElseThrow(() -> new Exception("Comment not found"));
    return reactionRepository.getCommReactions(commentId);
  }
}
