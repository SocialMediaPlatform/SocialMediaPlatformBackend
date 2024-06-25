package com.social_media_platform.social_media_platform_backend.controllers.responses;

import com.social_media_platform.social_media_platform_backend.databaseTables.ConversationMessage;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetConversationWithUserResponse {
    private Long conversationId;
    private List<MessageResponse> messages;

    public GetConversationWithUserResponse(Long conversationId, List<ConversationMessage> messages) {
        this.conversationId = conversationId;
        this.messages = messages.stream()
                .map(MessageResponse::new)
                .collect(Collectors.toList());
    }

}
