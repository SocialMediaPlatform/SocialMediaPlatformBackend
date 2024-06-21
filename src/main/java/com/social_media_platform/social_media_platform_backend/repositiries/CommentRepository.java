package com.social_media_platform.social_media_platform_backend.repositiries;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.social_media_platform.social_media_platform_backend.databaseTables.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.post.postId = :postId")
    public List<Comment> findByPostId(Long postId);

    @Query("SELECT c FROM Comment c WHERE c.mainComment.commentId = :mainCommentId")
    public Set<Comment> findByMainCommentId(Long mainCommentId);
}
