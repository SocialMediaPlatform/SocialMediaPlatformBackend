package com.social_media_platform.social_media_platform_backend.controllers.responses;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.social_media_platform.social_media_platform_backend.databaseTables.Conversation;
import com.social_media_platform.social_media_platform_backend.databaseTables.ConversationMessage;

import lombok.Data;

@Data
public class ConversationResponse {
    private Long conversationId;
    private Set<UserResponse> recipients;
    private List<MessageResponse> messages;

    public ConversationResponse() {}

    public ConversationResponse(Conversation conversation) {
        this.conversationId = conversation.getConversationId();
        this.recipients = conversation.getUsers().stream()
                .map(UserResponse::new)
                .collect(Collectors.toSet());
        this.messages = conversation.getConversationMessages().stream()
                .map(MessageResponse::new)
                .collect(Collectors.toList());
    }

    @Data
    public static class MessageResponse {
        private Long messageId;
        private String messageContent;
        private Date messageDate;
        private Long senderUserId;

        public MessageResponse() {}

        public MessageResponse(ConversationMessage message) {
            this.messageId = message.getMessageId();
            this.messageContent = message.getMessageContent();
            this.messageDate = message.getMessageDate();
            this.senderUserId = message.getUser().getUserId();
        }
    }
}
