package com.social_media_platform.social_media_platform_backend.controllers.requests;

import java.util.List;

import lombok.Data;

@Data
public class CreateConversationRequest {
  private List<Long> recipientUserIds;
  private String messageContent;
}
