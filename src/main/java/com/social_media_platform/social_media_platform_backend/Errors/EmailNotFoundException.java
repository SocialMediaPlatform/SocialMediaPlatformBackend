package com.social_media_platform.social_media_platform_backend.Errors;

public class EmailNotFoundException extends RuntimeException {
  public EmailNotFoundException(String message) {
    super(message);
  }
}
