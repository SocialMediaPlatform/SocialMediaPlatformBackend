package com.social_media_platform.social_media_platform_backend.controllers;

import com.social_media_platform.social_media_platform_backend.Helpers.GroupConversationInfo;
import com.social_media_platform.social_media_platform_backend.controllers.responses.ConversationResponse;
import com.social_media_platform.social_media_platform_backend.controllers.responses.MessageResponse;
import com.social_media_platform.social_media_platform_backend.controllers.responses.GetConversationWithUserResponse;
import com.social_media_platform.social_media_platform_backend.databaseTables.Conversation;
import com.social_media_platform.social_media_platform_backend.services.ConversationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.social_media_platform.social_media_platform_backend.controllers.requests.CreateConversationRequest;
import com.social_media_platform.social_media_platform_backend.controllers.requests.SendMessageRequest;
import com.social_media_platform.social_media_platform_backend.databaseTables.ConversationMessage;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody SendMessageRequest sendMessageRequest) {
        UserDetails currentUserDetails = getCurrentUserDetails();
        ConversationMessage message = conversationService.sendMessage(sendMessageRequest, currentUserDetails);
        try {
            if (message == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            MessageResponse response = new MessageResponse(message);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("create")
    public ResponseEntity<ConversationResponse> createConversation(@RequestBody CreateConversationRequest createConversationRequest) {
        UserDetails currenUserDetails = getCurrentUserDetails();
        Conversation createdConversation = conversationService.createConversation(createConversationRequest, currenUserDetails);
        return new ResponseEntity<>(new ConversationResponse(createdConversation), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/conversation")
    public ResponseEntity<GetConversationWithUserResponse> getConversationWithUser(
            @PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "0") Integer start,
            @RequestParam(required = false, defaultValue = "20") Integer limit) {

        UserDetails currenUserDetails = getCurrentUserDetails();
        Long conversationId = conversationService.getConversationsIdWithUser(userId, currenUserDetails);

        if (conversationId == null) {
            return new ResponseEntity<>(new GetConversationWithUserResponse(null, new ArrayList<>()), HttpStatus.OK);
        }

        List<ConversationMessage> conversationMessages = conversationService.getConversationMessages(conversationId, start, limit);
        return new ResponseEntity<>(new GetConversationWithUserResponse(conversationId, conversationMessages), HttpStatus.OK);
    }

    @GetMapping("/{conversationId}/messages")
    public ResponseEntity<List<MessageResponse>> getConversationMessages(
            @PathVariable Long conversationId,
            @RequestParam(required = false, defaultValue = "0") Integer start,
            @RequestParam(required = false, defaultValue = "20") Integer limit) {
        UserDetails currentUserDetails = getCurrentUserDetails();
        if (!conversationService.isUserInConversation(conversationId, currentUserDetails)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<ConversationMessage> messages = conversationService.getConversationMessages(conversationId, start, limit);
        List<MessageResponse> responseMessages = messages.stream()
                .map(MessageResponse::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseMessages, HttpStatus.OK);
    }

    @GetMapping("/groupConversations")
    public ResponseEntity<List<GroupConversationInfo>> getUserGroupConversations() {
        UserDetails currentUserDetails = getCurrentUserDetails();
        List<GroupConversationInfo> groupConversationInfo = conversationService.getGroupConversationDetails(currentUserDetails);

        if (groupConversationInfo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(groupConversationInfo, HttpStatus.OK);
    }

    private UserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        return (UserDetails) principal;
    }
}
