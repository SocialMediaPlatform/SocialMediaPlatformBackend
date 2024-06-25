package com.social_media_platform.social_media_platform_backend.controllers.responses;

import java.util.stream.Collectors;
import java.util.Set;
import java.util.List;

import com.social_media_platform.social_media_platform_backend.databaseTables.Conversation;

import lombok.Data;

@Data
public class ConversationResponse {
  private Long conversationId;
  private Set<UserResponse> recipients;
  private List<MessageResponse> messages;

  public ConversationResponse() {}

  public ConversationResponse(Conversation conversation) {
    this.conversationId = conversation.getConversationId();
    this.recipients =
        conversation.getUsers().stream().map(UserResponse::new).collect(Collectors.toSet());
    this.messages =
        conversation.getConversationMessages().stream()
            .map(MessageResponse::new)
            .collect(Collectors.toList());
  }
}
