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
import com.social_media_platform.social_media_platform_backend.databaseTables.User;
import com.social_media_platform.social_media_platform_backend.controllers.requests.CreateConversationRequest;
import com.social_media_platform.social_media_platform_backend.controllers.requests.SendMessageRequest;
import com.social_media_platform.social_media_platform_backend.databaseTables.ConversationMessage;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/conversations")
public class ConversationController {

  private final ConversationService conversationService;

  public ConversationController(ConversationService conversationService) {
    this.conversationService = conversationService;
  }

  @PostMapping("/sendMessage")
  public ResponseEntity<MessageResponse> sendMessage(
      @RequestBody SendMessageRequest sendMessageRequest) {
    User currentUser = getCurrentUser();
    ConversationMessage message = conversationService.sendMessage(sendMessageRequest, currentUser);
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
  public ResponseEntity<ConversationResponse> createConversation(
      @RequestBody CreateConversationRequest createConversationRequest) {
    User currentUser = getCurrentUser();
    Conversation createdConversation =
        conversationService.createConversation(createConversationRequest, currentUser);
    return new ResponseEntity<>(new ConversationResponse(createdConversation), HttpStatus.CREATED);
  }

  @GetMapping("/{userId}/conversation")
  public ResponseEntity<GetConversationWithUserResponse> getConversationWithUser(
      @PathVariable Long userId,
      @RequestParam(required = false, defaultValue = "0") Integer start,
      @RequestParam(required = false, defaultValue = "20") Integer limit) {

    User currentUser = getCurrentUser();
    Long conversationId = conversationService.getConversationsIdWithUser(userId, currentUser);

    if (conversationId == null) {
      return new ResponseEntity<>(
          new GetConversationWithUserResponse(null, new ArrayList<>()), HttpStatus.OK);
    }

    List<ConversationMessage> conversationMessages =
        conversationService.getConversationMessages(conversationId, start, limit);
    return new ResponseEntity<>(
        new GetConversationWithUserResponse(conversationId, conversationMessages), HttpStatus.OK);
  }

  @GetMapping("/{conversationId}/messages")
  public ResponseEntity<List<MessageResponse>> getConversationMessages(
      @PathVariable Long conversationId,
      @RequestParam(required = false, defaultValue = "0") Integer start,
      @RequestParam(required = false, defaultValue = "20") Integer limit) {
    User currentUser = getCurrentUser();
    if (!conversationService.isUserInConversation(conversationId, currentUser)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    List<ConversationMessage> messages =
        conversationService.getConversationMessages(conversationId, start, limit);
    List<MessageResponse> responseMessages =
        messages.stream().map(MessageResponse::new).collect(Collectors.toList());

    return new ResponseEntity<>(responseMessages, HttpStatus.OK);
  }

  @GetMapping("/groupConversations")
  public ResponseEntity<List<GroupConversationInfo>> getUserGroupConversations() {
    User currentUser = getCurrentUser();
    List<GroupConversationInfo> groupConversationInfo =
        conversationService.getGroupConversationDetails(currentUser);

    if (groupConversationInfo.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(groupConversationInfo, HttpStatus.OK);
  }

  private User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null
        || !authentication.isAuthenticated()
        || authentication instanceof AnonymousAuthenticationToken) {
      throw new IllegalStateException("No authenticated user available");
    }

    Object principal = authentication.getPrincipal();
    System.out.println(principal);

    if (principal instanceof Optional) {
      Optional<?> optionalPrincipal = (Optional<?>) principal;
      if (optionalPrincipal.isPresent() && optionalPrincipal.get() instanceof User) {
        return (User) optionalPrincipal.get();
      } else {
        throw new IllegalStateException(
            "Authenticated principal is not present or is not an instance of User");
      }
    } else if (principal instanceof User) {
      return (User) principal;
    }

    throw new IllegalStateException(
        "Authenticated principal is not an instance of User and is of type: "
            + principal.getClass());
  }
}
