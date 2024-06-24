package com.social_media_platform.social_media_platform_backend.controllers;

import com.social_media_platform.social_media_platform_backend.controllers.responses.ConversationResponse;
import com.social_media_platform.social_media_platform_backend.databaseTables.Conversation;
import com.social_media_platform.social_media_platform_backend.services.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.social_media_platform.social_media_platform_backend.controllers.requests.CreateConversationRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/api/v1/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    @Autowired
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping("create")
    public ResponseEntity<ConversationResponse> createConversation(@RequestBody CreateConversationRequest createConversationRequest) {
        System.out.println("Endpoint was accessed");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        System.out.println("Principal type: " + principal.getClass().getName());
        System.out.println("Principal value: " + principal.toString());
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Conversation createdConversation = conversationService.createConversation(createConversationRequest, userDetails);

        return new ResponseEntity<>(new ConversationResponse(createdConversation), HttpStatus.CREATED);
    }
}
