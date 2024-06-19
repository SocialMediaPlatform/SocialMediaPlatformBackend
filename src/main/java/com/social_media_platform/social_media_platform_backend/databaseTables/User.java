package com.social_media_platform.social_media_platform_backend.databaseTables;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  private String username;
  private String email;
  private String password;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<UserRelation> userRelations;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<Post> posts;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<ConversationMessage> conversationMessages;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "user_conversation",
      joinColumns = @JoinColumn(name = "userId"),
      inverseJoinColumns = @JoinColumn(name = "conversationId"))
  private Set<Conversation> conversations;
}
