package com.social_media_platform.social_media_platform_backend.controllers;

import com.social_media_platform.social_media_platform_backend.databaseTables.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.social_media_platform.social_media_platform_backend.controllers.requests.ChangePasswordRequest;
import com.social_media_platform.social_media_platform_backend.controllers.responses.UserResponse;
import com.social_media_platform.social_media_platform_backend.services.JwtService;
import com.social_media_platform.social_media_platform_backend.services.UserRelationService;
import com.social_media_platform.social_media_platform_backend.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/user")
public class UserController {
  private final UserService userService;
  private final JwtService jwtService;
  private final UserRelationService userRelationService;

  public UserController(UserService userService, JwtService jwtService, UserRelationService userRelationService) {
    this.userService = userService;
    this.jwtService = jwtService;
    this.userRelationService = userRelationService;
  }

  @GetMapping({ "{userId}" })
  public ResponseEntity<?> getUserInfo(@PathVariable Long userId, @RequestHeader(name = "Authorization") String token) {
    try {
      return ResponseEntity.ok(new UserResponse(userService.getUserInfo(userId), userRelationService.areUsersFollowed(
          jwtService.extractUserId(token.split(" ")[1].trim()), userId)));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("setUsername/{newUsername}")
  public ResponseEntity<?> postMethodName(
      @PathVariable String newUsername, @RequestHeader(name = "Authorization") String token) {
    try {
      userService.setUsername(jwtService.extractUserId(token.split(" ")[1].trim()), newUsername);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("changePassword")
  public ResponseEntity<?> changePassowrd(
      @RequestBody ChangePasswordRequest changePasswordRequest,
      @RequestHeader(name = "Authorization") String token) {
    try {
      userService.setPassword(
          jwtService.extractUserId(token.split(" ")[1].trim()),
          changePasswordRequest.getOldPassword(),
          changePasswordRequest.getNewPassword());
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/search")
  public ResponseEntity<List<User>> searchForUsers(
      @RequestParam String username, @RequestHeader(name = "Authorization") String token) {
    List<User> users = userService.getNonBlockedUsers(
        username, jwtService.extractUserId(token.split(" ")[1].trim()));
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @GetMapping("getUser/{username}")
  public ResponseEntity<?> getUserId(@PathVariable String username) {
    try {
      return ResponseEntity.ok(new UserResponse(userService.getUser(username)));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
