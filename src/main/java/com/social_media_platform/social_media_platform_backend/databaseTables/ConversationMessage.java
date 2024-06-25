package com.social_media_platform.social_media_platform_backend.databaseTables;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
public class ConversationMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long messageId;

  private String messageContent;

  private Date messageDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "conversation_id", nullable = false)
  private Conversation conversation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Override
  public String toString() {
    return "ConversationMessage{"
        + "messageId="
        + messageId
        + ", messageContent='"
        + messageContent
        + '\''
        + ", messageDate="
        + messageDate
        + ", userId="
        + (user != null ? user.getUserId() : null)
        + '}';
  }
}
