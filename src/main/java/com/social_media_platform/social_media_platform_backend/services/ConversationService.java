package com.social_media_platform.social_media_platform_backend.services;

import com.social_media_platform.social_media_platform_backend.databaseTables.Conversation;
import com.social_media_platform.social_media_platform_backend.databaseTables.ConversationMessage;
import com.social_media_platform.social_media_platform_backend.databaseTables.User;
import com.social_media_platform.social_media_platform_backend.repositiries.ConversationMessageRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.ConversationRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.social_media_platform.social_media_platform_backend.controllers.requests.CreateConversationRequest;
import org.springframework.data.domain.PageRequest;
import com.social_media_platform.social_media_platform_backend.Helpers.GroupConversationInfo;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final ConversationMessageRepository conversationMessageRepository;

    @Autowired
    public ConversationService(
            ConversationRepository conversationRepository,
            UserRepository userRepository,
            ConversationMessageRepository conversationMessageRepository
    ) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        this.conversationMessageRepository = conversationMessageRepository;
    }

    @Transactional
    public Conversation createConversation(CreateConversationRequest request, UserDetails initiatingUsersDetails) {
        User initiatingUser = userRepository.findByUsername(initiatingUsersDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("User not found: " + initiatingUsersDetails.getUsername()));

        Conversation newConversation = createNewConversation(request, initiatingUser);

        return conversationRepository.save(newConversation);
    }

    public Long getConversationsIdWithUser(Long OtherUserId, UserDetails initiatingUsersDetails) {
        User initiatingUser = userRepository.findByUsername(initiatingUsersDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("User not found: " + initiatingUsersDetails.getUsername()));

        List<Long> userIds = Arrays.asList(initiatingUser.getUserId(), OtherUserId);
        List<Long> conversationIds = conversationRepository.findConversationIdByUserIds(userIds);

        return conversationIds.stream().findFirst().orElse(null);
    }

    public List<ConversationMessage> getConversationMessages(Long conversationId, int offset, int limit) {
        System.out.println("offset and lmit:" + offset + limit);
        System.out.println("ConversationId:" + conversationId);
        List<ConversationMessage> conversation_messages = conversationMessageRepository.findMessagesByConversationId(conversationRepository.findById(conversationId), offset, limit);
        System.out.println(conversation_messages);
        return conversation_messages;
    }

    public List<GroupConversationInfo> getGroupConversationDetails(UserDetails userDetails) {
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        if (user.isEmpty()) {
            throw new NoSuchElementException("No user found with username: " + userDetails.getUsername());
        }

        List<Long> conversationIds = conversationRepository.findGroupConversationIdsByUserId(user.get().getUserId());
        return conversationIds.stream().map(conversationId -> {
            List<String> usernames = userRepository.findUsernamesByConversationId(conversationId);
            return new GroupConversationInfo(conversationId, usernames);
        }).collect(Collectors.toList());
    }

    public Boolean isUserInConversation(Long conversationId, UserDetails initiatingUsersDetails) {
        Optional<User> user = userRepository.findByUsername(initiatingUsersDetails.getUsername());
        return conversationRepository.existsByUserIdAndConversationId(user.get().getUserId(), conversationId);
    }

    private Conversation createNewConversation(CreateConversationRequest request, User initiatingUser) {
        Conversation newConversation = new Conversation();
        addUsersToConversation(request, newConversation);
        ConversationMessage newMessage = createConversationMessage(request, initiatingUser, newConversation);
        newConversation.addMessage(newMessage);
        newConversation.addUser(initiatingUser);
        return newConversation;
    }

    private void addUsersToConversation(CreateConversationRequest request, Conversation conversation) {
        request.getRecipientUserIds().forEach(id -> {
            User recipient = getUserById(id);
            conversation.addUser(recipient);
        });
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found with ID: " + id));
    }

    private ConversationMessage createConversationMessage(CreateConversationRequest request, User messageSender, Conversation conversation) {
        ConversationMessage newMessage = new ConversationMessage();
        newMessage.setMessageContent(request.getMessageContent());
        newMessage.setMessageDate(new Date());
        newMessage.setUser(messageSender);
        newMessage.setConversation(conversation);
        return newMessage;
    }
}
