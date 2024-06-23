package com.social_media_platform.social_media_platform_backend.Helpers;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class PasswordResetRequest {
  private String email;
}
