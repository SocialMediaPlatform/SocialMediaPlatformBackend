package com.social_media_platform.social_media_platform_backend.controllers.responses;

import com.social_media_platform.social_media_platform_backend.databaseTables.Reaction;

import lombok.Data;

@Data
public class CommentReactionResponse {
  Long reactionTypeId;
  Long reactionId;
  UserResponse user;

  public CommentReactionResponse(Reaction commentReaction) {
    this.reactionTypeId = commentReaction.getReactionType().getReactionTypeId();
    this.reactionId = commentReaction.getReactionId();
    this.user = new UserResponse(commentReaction.getUser());
  }
}
