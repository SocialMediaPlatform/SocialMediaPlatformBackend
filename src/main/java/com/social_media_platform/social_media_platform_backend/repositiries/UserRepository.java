package com.social_media_platform.social_media_platform_backend.repositiries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.social_media_platform.social_media_platform_backend.databaseTables.User;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.List;

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

  @Query(
      value =
          "SELECT u.* FROM user_conversation uc JOIN Users u ON u.user_id = uc.user_id WHERE"
              + " uc.conversation_id = :conversationId",
      nativeQuery = true)
  List<User> findUsersByConversationId(@Param("conversationId") Long conversationId);

  @Query(
      "select u from User u full join u.userRelations ur full join ur.relationType rt where"
          + " u.username like %:username% and (rt.relationTypeName is null or rt.relationTypeName"
          + " <> 'blocked') and u.userId <> :userId")
  List<User> findUsersByUsernamePattern(
      @Param("username") String username, @Param("userId") Long userId);
}
