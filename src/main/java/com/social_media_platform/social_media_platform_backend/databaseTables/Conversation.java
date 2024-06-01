package com.social_media_platform.social_media_platform_backend.databaseTables;

import lombok.Data;
import jakarta.persistence.*;

import java.util.Set;

@Data
@Entity
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conversationId;
    private String conversationName;

    @OneToMany(mappedBy = "conversation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ConversationMessage> conversationMessages;

    @ManyToMany(mappedBy = "conversations", fetch = FetchType.LAZY)
    private Set<User> users;
}
