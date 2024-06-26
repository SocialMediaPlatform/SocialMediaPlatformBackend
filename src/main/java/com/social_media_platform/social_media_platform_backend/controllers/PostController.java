package com.social_media_platform.social_media_platform_backend.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.social_media_platform.social_media_platform_backend.controllers.requests.AddPostRequest;
import com.social_media_platform.social_media_platform_backend.controllers.responses.PostResponse;
import com.social_media_platform.social_media_platform_backend.controllers.responses.PostReactionResponse;
import com.social_media_platform.social_media_platform_backend.databaseTables.Post;
import com.social_media_platform.social_media_platform_backend.databaseTables.User;
import com.social_media_platform.social_media_platform_backend.services.JwtService;
import com.social_media_platform.social_media_platform_backend.services.PostService;
import com.social_media_platform.social_media_platform_backend.services.ReactionService;
import com.social_media_platform.social_media_platform_backend.services.UserRelationService;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
  private final PostService postService;
  private final UserRelationService userRelationService;
  private final ReactionService reactionService;
  private final JwtService jwtService;

  public PostController(
      PostService postService,
      UserRelationService userRelationService,
      ReactionService reactionService,
      JwtService jwtService) {
    this.postService = postService;
    this.userRelationService = userRelationService;
    this.reactionService = reactionService;
    this.jwtService = jwtService;
  }

  @GetMapping("{userId}")
  public ResponseEntity<List<PostResponse>> getUserPosts(@PathVariable Long userId) {
    ArrayList<PostResponse> postResponses = new ArrayList<>();
    for (Post post : postService.getUserPosts(userId)) {
      PostReactionResponse userPostReaction = null;
      try {
        userPostReaction =
            new PostReactionResponse(reactionService.getUserPostReaction(post.getPostId(), userId));
      } catch (Exception e) {
      }
      postResponses.add(
          new PostResponse(
              post,
              postService.getPostReactionsCount(post.getPostId()),
              postService.getPostCommentsCount(post.getPostId()),
              userPostReaction));
    }
    return new ResponseEntity<>(postResponses, HttpStatus.OK);
  }

  @GetMapping("post/{postId}")
  public ResponseEntity<PostResponse> getPost(
      @PathVariable Long postId, @RequestHeader(name = "Authorization") String token) {
    try {
      Post post = postService.getPost(postId);
      PostReactionResponse userPostReaction = null;
      try {
        userPostReaction =
            new PostReactionResponse(
                reactionService.getUserPostReaction(
                    post.getPostId(), jwtService.extractUserId(token.split(" ")[1].trim())));
      } catch (Exception e) {
      }
      return new ResponseEntity<>(
          new PostResponse(
              post,
              postService.getPostReactionsCount(post.getPostId()),
              postService.getPostCommentsCount(post.getPostId()),
              userPostReaction),
          HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("add")
  public ResponseEntity<?> createPost(@RequestBody AddPostRequest post) {
    try {
      postService.createPost(post.getUserId(), new Post(post.getPostDate(), post.getPostContent()));
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("followed/{userId}")
  public ResponseEntity<List<PostResponse>> getFollowedPosts(@PathVariable Long userId) {
    List<User> users = new ArrayList<>();
    try {
      for (var userRelation : userRelationService.getfollowedUsers(userId)) {
        if (userRelationService.areUsersBlocked(userId, userRelation.getTargetUser().getUserId())) {
          continue;
        }
        users.add(userRelation.getTargetUser());
      }
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    List<PostResponse> postResponses = new ArrayList<>();
    for (var post : postService.getUsersPosts(users)) {
      PostReactionResponse userPostReaction = null;
      try {
        userPostReaction =
            new PostReactionResponse(reactionService.getUserPostReaction(post.getPostId(), userId));
      } catch (Exception e) {
      }
      postResponses.add(
          new PostResponse(
              post,
              postService.getPostReactionsCount(post.getPostId()),
              postService.getPostCommentsCount(post.getPostId()),
              userPostReaction));
    }
    return new ResponseEntity<>(postResponses, HttpStatus.OK);
  }

  @GetMapping("reactions/{postId}")
  public ResponseEntity<?> addPostReaction(@PathVariable Long postId) {
    try {
      List<PostReactionResponse> reactions = new ArrayList<>();
      for (var reaction : postService.getPostReactions(postId)) {
        reactions.add(new PostReactionResponse(reaction));
      }
      return new ResponseEntity<>(reactions, HttpStatus.OK);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
