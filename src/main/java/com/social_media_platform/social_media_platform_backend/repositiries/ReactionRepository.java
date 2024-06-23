package com.social_media_platform.social_media_platform_backend.repositiries;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.social_media_platform.social_media_platform_backend.databaseTables.Reaction;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
  @Query("SELECT r FROM Reaction r WHERE r.post.postId = :postId")
  List<Reaction> getPostReactions(Long postId);

  @Query("SELECT r FROM Reaction r WHERE r.comment.commentId = :commentId")
  List<Reaction> getCommReactions(Long commentId);

  @Query("SELECT r FROM Reaction r WHERE r.post.postId = :postId AND r.user.userId = :userId")
  List<Reaction> findByPostIdAndUserId(Long postId, Long userId);

  @Query(
      "SELECT r FROM Reaction r WHERE r.comment.commentId = :commentId AND r.user.userId = :userId")
  List<Reaction> findByCommentIdAndUserId(Long commentId, Long userId);
}
