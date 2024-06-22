package com.social_media_platform.social_media_platform_backend.controllers;

import com.social_media_platform.social_media_platform_backend.Errors.UserAlreadyExistsException;
import com.social_media_platform.social_media_platform_backend.Helpers.AuthenticationRequest;
import com.social_media_platform.social_media_platform_backend.Helpers.AuthenticationResponse;
import com.social_media_platform.social_media_platform_backend.Helpers.RegisterRequest;
import com.social_media_platform.social_media_platform_backend.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
