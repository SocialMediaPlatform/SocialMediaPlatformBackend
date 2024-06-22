package com.social_media_platform.social_media_platform_backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social_media_platform.social_media_platform_backend.controllers.requests.AddCommentReaction;
import com.social_media_platform.social_media_platform_backend.controllers.requests.AddPostReaction;
import com.social_media_platform.social_media_platform_backend.services.ReactionService;

@RestController
@RequestMapping("/api/v1/reaction")
public class ReactionController {
  private final ReactionService reactionService;

  public ReactionController(ReactionService reactionService) {
    this.reactionService = reactionService;
  }

  @PostMapping("addPostReaction")
  public ResponseEntity<?> addPostReaction(@RequestBody AddPostReaction addPostReaction) {
    try {
      reactionService.addPostReaction(
          addPostReaction.getPostId(),
          addPostReaction.getReactionTypeId(),
          addPostReaction.getUserId());
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("addCommentReaction")
  public ResponseEntity<?> addCommentReaction(@RequestBody AddCommentReaction addCommentReaction) {
    try {
      reactionService.addCommentReaction(
          addCommentReaction.getCommentId(),
          addCommentReaction.getReactionTypeId(),
          addCommentReaction.getUserId());
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
