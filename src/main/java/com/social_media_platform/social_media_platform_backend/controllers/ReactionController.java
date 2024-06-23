package com.social_media_platform.social_media_platform_backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social_media_platform.social_media_platform_backend.controllers.requests.AddCommentReaction;
import com.social_media_platform.social_media_platform_backend.controllers.requests.AddPostReaction;
import com.social_media_platform.social_media_platform_backend.controllers.requests.UserCommentReactionRequest;
import com.social_media_platform.social_media_platform_backend.controllers.requests.UserPostReactionRequest;
import com.social_media_platform.social_media_platform_backend.controllers.responses.CommentReactionResponse;
import com.social_media_platform.social_media_platform_backend.controllers.responses.PostReactionResponse;
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

  @DeleteMapping("removePostReaction")
  public ResponseEntity<?> removePostReaction(
      @RequestBody UserPostReactionRequest removePostReaction) {
    try {
      reactionService.removePostReaction(
          removePostReaction.getPostId(), removePostReaction.getUserId());
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("removeCommentReaction")
  public ResponseEntity<?> removeCommentReaction(
      @RequestBody UserCommentReactionRequest removeCommentReaction) {
    try {
      reactionService.removeCommentReaction(
          removeCommentReaction.getCommentId(), removeCommentReaction.getUserId());
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("userPostReaction")
  public ResponseEntity<?> getUserPostReaction(@RequestBody UserPostReactionRequest postReaction) {
    try {
      return new ResponseEntity<>(
          new PostReactionResponse(
              reactionService.getUserPostReaction(
                  postReaction.getPostId(), postReaction.getUserId())),
          HttpStatus.OK);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("userCommentReaction")
  public ResponseEntity<?> getUserCommentReaction(
      @RequestBody UserCommentReactionRequest commentReaction) {
    try {
      return new ResponseEntity<>(
          new CommentReactionResponse(
              reactionService.getUserCommentReaction(
                  commentReaction.getCommentId(), commentReaction.getUserId())),
          HttpStatus.OK);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
