package com.social_media_platform.social_media_platform_backend.controllers.responses;

import java.util.Date;

import com.social_media_platform.social_media_platform_backend.databaseTables.Post;

import lombok.Data;

@Data
public class PostResponse {
  public Long postId;
  private Date postDate;
  private String postContent;
  private UserResponse user;
  private int reactionsCount;
  private int commentsCount;
  private PostReactionResponse postReactionResponse;

  public PostResponse(
      Post post, int reactionsCount, int commentsCount, PostReactionResponse postReactionResponse) {
    this.postId = post.getPostId();
    this.postDate = post.getPostDate();
    this.postContent = post.getPostContent();
    this.user = new UserResponse(post.getUser());
    this.reactionsCount = reactionsCount;
    this.commentsCount = commentsCount;
    this.postReactionResponse = postReactionResponse;
  }
}
