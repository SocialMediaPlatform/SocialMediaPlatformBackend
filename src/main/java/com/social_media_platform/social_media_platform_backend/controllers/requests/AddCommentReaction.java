package com.social_media_platform.social_media_platform_backend.controllers.requests;

import lombok.Data;

@Data
public class AddCommentReaction {
    private Long commentId;
    private Long reactionTypeId;
    private Long userId;
}
