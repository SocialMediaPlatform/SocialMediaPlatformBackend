package com.social_media_platform.social_media_platform_backend.controllers.responses;

import com.social_media_platform.social_media_platform_backend.databaseTables.User;

import lombok.Data;

@Data
public class UserResponse {
  private Long userId;
  private String username;
  private String email;

  public UserResponse(User user) {
    this.userId = user.getUserId();
    this.username = user.getUsername();
    this.email = user.getEmail();
  }
}
