package com.social_media_platform.social_media_platform_backend.controllers.requests;

import lombok.Data;

@Data
public class RemovePostReaction {
    private Long postId;
    private Long userId;
}
