package com.social_media_platform.social_media_platform_backend.service_test;

import com.social_media_platform.social_media_platform_backend.databaseTables.User;
import com.social_media_platform.social_media_platform_backend.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class JwtServiceTest {

  @Autowired private JwtService jwtService;
  private String token;
  private User user;

  @BeforeEach
  void init() {
    user = new User("username", "email@example.com", "password");
    token = jwtService.generateToken(user);
  }

  @Test
  public void testGenerateJwtToken() {
    assertNotNull(token);
  }

  @Test
  public void testExtractEmail() {
    User user = new User("username", "email@example.com", "password");
    assertEquals(user.getEmail(), jwtService.extractUsername(token));
  }

  @Test
  public void testExtractUserId() {
    User user = new User("username", "email@example.com", "password");
    assertEquals(user.getUserId(), jwtService.extractUserId(token));
  }
}
