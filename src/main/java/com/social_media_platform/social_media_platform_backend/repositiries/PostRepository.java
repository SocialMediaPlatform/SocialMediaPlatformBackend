package com.social_media_platform.social_media_platform_backend.repositiries;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.social_media_platform.social_media_platform_backend.databaseTables.Post;
import com.social_media_platform.social_media_platform_backend.databaseTables.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
  @Query("SELECT p FROM Post p WHERE p.user.userId = :userId")
  List<Post> getUserPosts(Long userId);

  @Query("SELECT p FROM Post p WHERE p.user IN :users")
  List<Post> getUsersPosts(List<User> users);
}
