package com.social_media_platform.social_media_platform_backend.controllers.requests;

import java.util.Date;

import lombok.Data;

@Data
public class AddSubCommentRequest {
  private Long userId;
  private String commentContents;
  private Date commentDate;
  private Long mainCommentId;
}
