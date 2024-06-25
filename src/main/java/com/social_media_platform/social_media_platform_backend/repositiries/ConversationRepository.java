package com.social_media_platform.social_media_platform_backend.repositiries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.social_media_platform.social_media_platform_backend.databaseTables.Conversation;
import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
  @Query(
      value =
          "SELECT conversation_id FROM user_conversation GROUP BY conversation_id HAVING"
              + " COUNT(DISTINCT user_id) = 2 AND SUM(CASE WHEN user_id IN (:userIds) THEN 1 ELSE 0"
              + " END) = 2",
      nativeQuery = true)
  List<Long> findConversationIdByUserIds(@Param("userIds") List<Long> userIds);

  @Query(
      value =
          "SELECT conversation_id FROM user_conversation WHERE conversation_id IN (SELECT"
              + " conversation_id FROM user_conversation WHERE user_id = :userId) GROUP BY"
              + " conversation_id HAVING COUNT(DISTINCT user_id) > 2",
      nativeQuery = true)
  List<Long> findGroupConversationIdsByUserId(@Param("userId") Long userId);

  @Query(
      value =
          "SELECT COUNT(*) > 0 FROM user_conversation WHERE user_id = :userId AND conversation_id ="
              + " :conversationId",
      nativeQuery = true)
  boolean existsByUserIdAndConversationId(
      @Param("userId") Long userId, @Param("conversationId") Long conversationId);
}
