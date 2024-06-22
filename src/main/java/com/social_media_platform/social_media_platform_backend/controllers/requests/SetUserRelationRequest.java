package com.social_media_platform.social_media_platform_backend.controllers.requests;

import lombok.Data;

@Data
public class SetUserRelationRequest {
    private Long userId;
    private Long targetUserId;
    private Long relationTypeId;
}
