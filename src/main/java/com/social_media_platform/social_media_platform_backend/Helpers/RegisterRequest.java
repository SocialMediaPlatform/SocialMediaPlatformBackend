package com.social_media_platform.social_media_platform_backend.Helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
  private String email;
  private String password;
  private String username;
}
