package com.social_media_platform.social_media_platform_backend.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.social_media_platform.social_media_platform_backend.controllers.requests.AddMainCommentRequest;
import com.social_media_platform.social_media_platform_backend.controllers.requests.AddSubCommentRequest;
import com.social_media_platform.social_media_platform_backend.controllers.responses.CommentReactionResponse;
import com.social_media_platform.social_media_platform_backend.controllers.responses.CommentResponse;
import com.social_media_platform.social_media_platform_backend.services.CommentService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/comment")
public class CommentController {
  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @GetMapping("{postId}")
  public ResponseEntity<List<CommentResponse>> getCommentsInPost(@PathVariable Long postId) {
    try {
      var comments = new ArrayList<CommentResponse>();
      for (var comment : commentService.getCommentsInPost(postId)) {
        comments.add(new CommentResponse(comment));
      }
      return new ResponseEntity<>(comments, HttpStatus.OK);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("add")
  public ResponseEntity<?> addComment(@RequestBody AddMainCommentRequest addMainCommentRequest) {
    try {
      commentService.addComment(addMainCommentRequest);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("addResponse")
  public ResponseEntity<?> addComment(@RequestBody AddSubCommentRequest addMainCommentRequest) {
    try {
      commentService.addComment(addMainCommentRequest);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("reactions/{commentId}")
  public ResponseEntity<?> getCommentReactions(@PathVariable Long commentId) {
    try {
      var reactions = new ArrayList<CommentReactionResponse>();
      for (var reaction : commentService.getCommentReactions(commentId)) {
        reactions.add(new CommentReactionResponse(reaction));
      }
      return new ResponseEntity<>(reactions, HttpStatus.OK);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
