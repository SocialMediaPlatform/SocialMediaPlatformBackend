package com.social_media_platform.social_media_platform_backend.services;

import com.social_media_platform.social_media_platform_backend.Errors.UserAlreadyExistsException;
import com.social_media_platform.social_media_platform_backend.repositiries.UserRepository;
import com.social_media_platform.social_media_platform_backend.Helpers.AuthenticationResponse;
import com.social_media_platform.social_media_platform_backend.Helpers.AuthenticationRequest;
import com.social_media_platform.social_media_platform_backend.Helpers.RegisterRequest;
import com.social_media_platform.social_media_platform_backend.databaseTables.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationService(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      JwtService jwtService,
      AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    User user =
        userRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    String jwtToken = jwtService.generateToken(user);
    return new AuthenticationResponse(jwtToken);
  }

  public AuthenticationResponse register(RegisterRequest request) {
    String username = request.getUsername();
    String email = request.getEmail();
    String password = request.getPassword();
    Optional<User> existingUserEmail= userRepository.findByEmail(email);
    if (existingUserEmail.isPresent()) {
      throw new UserAlreadyExistsException("User with this email already exists");
    }
    Optional<User> existingUserUsername= userRepository.findByUsername(username);
    if (existingUserUsername.isPresent()) {
      throw new UserAlreadyExistsException("User with this username already exists");
    }
    User user =
        new User(
            username,
            email,
            passwordEncoder.encode(password));
    userRepository.save(user);
    String jwtToken = jwtService.generateToken(user);
    return new AuthenticationResponse(jwtToken);
  }
}
