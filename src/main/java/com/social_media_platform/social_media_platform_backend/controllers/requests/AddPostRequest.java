package com.social_media_platform.social_media_platform_backend.controllers.requests;

import java.util.Date;

import lombok.Data;

@Data
public class AddPostRequest {
    public Long postId;
    private Date postDate;
    private String postContent;
    private Long userId;
}
