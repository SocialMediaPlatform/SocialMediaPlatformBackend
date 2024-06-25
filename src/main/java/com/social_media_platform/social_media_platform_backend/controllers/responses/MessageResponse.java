package com.social_media_platform.social_media_platform_backend.controllers.responses;

import lombok.Data;
import com.social_media_platform.social_media_platform_backend.databaseTables.ConversationMessage;

import java.util.Date;

@Data
public class MessageResponse {
    private Long messageId;
    private String content;
    private Date messageDate;
    private Long senderId;

    public MessageResponse(ConversationMessage message) {
        this.messageId = message.getMessageId();
        this.content = message.getMessageContent();
        this.messageDate = message.getMessageDate();
        this.senderId = message.getUser().getUserId();
    }
}
