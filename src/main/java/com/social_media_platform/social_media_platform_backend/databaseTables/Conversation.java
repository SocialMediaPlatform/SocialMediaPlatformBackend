package com.social_media_platform.social_media_platform_backend.databaseTables;

import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(exclude = {"conversations", "conversationMessages"})
@Entity
public class Conversation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long conversationId;

  @OneToMany(mappedBy = "conversation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<ConversationMessage> conversationMessages = new HashSet<>();

  @ManyToMany(mappedBy = "conversations", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<User> users = new HashSet<>();

  public Conversation() {
  }

  public void addMessage(ConversationMessage message) {
    this.conversationMessages.add(message);
    message.setConversation(this);
  }

  public void addUser(User user) {
    this.users.add(user);
    user.getConversations().add(this);
  }

  public Set<Long> getUserIds() {
    return users.stream()
            .map(User::getUserId)
            .collect(Collectors.toSet());
  }

  public Set<ConversationMessage> getConversationMessages() {
    return new HashSet<>(conversationMessages);
  }
}