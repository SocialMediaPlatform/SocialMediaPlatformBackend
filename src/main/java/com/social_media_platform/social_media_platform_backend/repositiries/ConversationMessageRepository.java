package com.social_media_platform.social_media_platform_backend.repositiries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.social_media_platform.social_media_platform_backend.databaseTables.ConversationMessage;
import com.social_media_platform.social_media_platform_backend.databaseTables.Conversation;

import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationMessageRepository extends JpaRepository<ConversationMessage, Long> {

    @Query("SELECT cm FROM ConversationMessage cm WHERE cm.conversation = :conversationId ORDER BY cm.messageDate ASC LIMIT :limit OFFSET :offset")
    List<ConversationMessage> findMessagesByConversationId(@Param("conversationId") Optional<Conversation> conversation, @Param("limit") int limit, @Param("offset") int offset);
}
