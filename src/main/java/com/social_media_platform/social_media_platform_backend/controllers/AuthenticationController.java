package com.social_media_platform.social_media_platform_backend.controllers;

import com.social_media_platform.social_media_platform_backend.Errors.UserAlreadyExistsException;
import com.social_media_platform.social_media_platform_backend.Helpers.*;
import com.social_media_platform.social_media_platform_backend.services.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request) {
    return new ResponseEntity<>(authenticationService.authenticate(request), HttpStatus.OK);
  }

  @PostMapping("register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    try {
      AuthenticationResponse response = authenticationService.register(request);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (UserAlreadyExistsException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
  }

  @PostMapping("/password-reset-request")
  public ResponseEntity<?> resetPasswordRequest(
      @RequestBody PasswordResetRequest passwordResetRequest,
      final HttpServletRequest servletRequest) {
    try {
      String passwordResetUrl =
          authenticationService.forgetPassword(passwordResetRequest.getEmail(), servletRequest);
      if (!passwordResetUrl.isEmpty()) {
        return new ResponseEntity<>(
            "Password reset link has been sent to your email", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("User with this email not found", HttpStatus.NOT_FOUND);
      }
    } catch (MessagingException | UnsupportedEncodingException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/reset-password")
  public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordReq req) {
    String newPassword = req.getPassword();
    String token = req.getToken();
    String result = authenticationService.resetPassword(token, newPassword);
    if (result.equalsIgnoreCase("Password has been reset successfully")) {
      return new ResponseEntity<>(result, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
  }
}
