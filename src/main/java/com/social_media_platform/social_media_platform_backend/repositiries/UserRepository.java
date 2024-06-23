package com.social_media_platform.social_media_platform_backend.repositiries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.social_media_platform.social_media_platform_backend.databaseTables.User;

import jakarta.transaction.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);

  @Modifying
  @Transactional
  @Query("update User u set u.username = :newUsername where u.userId = :userId")
  void setUsername(Long userId, String newUsername);

  @Modifying
  @Transactional
  @Query("update User u set u.password = :newPassword where u.userId = :userId")
  void setPassword(Long userId, String newPassword);
}
