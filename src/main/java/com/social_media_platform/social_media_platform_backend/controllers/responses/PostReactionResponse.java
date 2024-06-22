package com.social_media_platform.social_media_platform_backend.controllers.responses;

import com.social_media_platform.social_media_platform_backend.databaseTables.Reaction;

import lombok.Data;

@Data
public class PostReactionResponse {
    Long reactionTypeId;
    Long reactionId;
    UserResponse user;

    public PostReactionResponse(Reaction reaction) {
        this.reactionTypeId = reaction.getReactionType().getReactionTypeId();
        this.reactionId = reaction.getReactionId();
        this.user = new UserResponse(reaction.getUser());
    }
}
