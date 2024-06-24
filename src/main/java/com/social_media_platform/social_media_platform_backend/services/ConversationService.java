package com.social_media_platform.social_media_platform_backend.services;

import com.social_media_platform.social_media_platform_backend.databaseTables.Conversation;
import com.social_media_platform.social_media_platform_backend.databaseTables.ConversationMessage;
import com.social_media_platform.social_media_platform_backend.databaseTables.User;
import com.social_media_platform.social_media_platform_backend.repositiries.ConversationRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.social_media_platform.social_media_platform_backend.controllers.requests.CreateConversationRequest;

import java.util.Date;

@Service
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    @Autowired
    public ConversationService(ConversationRepository conversationRepository, UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    public Conversation createConversation(CreateConversationRequest request, UserDetails userDetails) {
        User initiatingUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("User not found: " + userDetails.getUsername()));

        Conversation newConversation = new Conversation();

        request.getRecipientUserIds().forEach(id -> {
            User recipient = userRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("User not found with ID: " + id));
            newConversation.getUsers().add(recipient);
        });

        newConversation.getUsers().add(initiatingUser);

        ConversationMessage newMessage = new ConversationMessage();
        newMessage.setMessageContent(request.getMessageContent());
        newMessage.setMessageDate(request.getMessageDate());
        newMessage.setUser(initiatingUser);
        newMessage.setConversation(newConversation);
        
        newConversation.getConversationMessages().add(newMessage);

        return conversationRepository.save(newConversation);
    }
}
