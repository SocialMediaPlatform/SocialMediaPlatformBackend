package com.social_media_platform.social_media_platform_backend.repositiries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.social_media_platform.social_media_platform_backend.databaseTables.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {}
