package com.social_media_platform.social_media_platform_backend.services;

import com.social_media_platform.social_media_platform_backend.Errors.EmailNotFoundException;
import com.social_media_platform.social_media_platform_backend.Errors.UserAlreadyExistsException;
import com.social_media_platform.social_media_platform_backend.Helpers.RegistrationMailSender;
import com.social_media_platform.social_media_platform_backend.repositiries.PasswordResetTokenRepository;
import com.social_media_platform.social_media_platform_backend.repositiries.UserRepository;
import com.social_media_platform.social_media_platform_backend.Helpers.AuthenticationResponse;
import com.social_media_platform.social_media_platform_backend.Helpers.AuthenticationRequest;
import com.social_media_platform.social_media_platform_backend.Helpers.RegisterRequest;
import com.social_media_platform.social_media_platform_backend.databaseTables.User;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final PasswordResetTokenService passwordResetTokenService;

  private final RegistrationMailSender registrationMailSender;
  public AuthenticationService(
          UserRepository userRepository,
          PasswordEncoder passwordEncoder,
          JwtService jwtService,
          AuthenticationManager authenticationManager,
          PasswordResetTokenService passwordResetTokenService,
          RegistrationMailSender registrationMailSender) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
    this.passwordResetTokenService = passwordResetTokenService;
    this.registrationMailSender = registrationMailSender;
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
    Optional<User> existingUserEmail = userRepository.findByEmail(email);
    if (existingUserEmail.isPresent()) {
      throw new UserAlreadyExistsException("User with this email already exists");
    }
    Optional<User> existingUserUsername = userRepository.findByUsername(username);
    if (existingUserUsername.isPresent()) {
      throw new UserAlreadyExistsException("User with this username already exists");
    }
    User user = new User(username, email, passwordEncoder.encode(password));
    userRepository.save(user);
    String jwtToken = jwtService.generateToken(user);
    return new AuthenticationResponse(jwtToken);
  }

  public String forgetPassword(String mail, HttpServletRequest servletRequest) throws MessagingException, UnsupportedEncodingException {

    Optional<User> user = userRepository.findByEmail(mail);
    String passwordResetUrl = "";
    if (user.isPresent()) {
      String passwordResetToken = UUID.randomUUID().toString();
      passwordResetTokenService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
      passwordResetUrl = passwordResetEmailLink(user.get(), applicationUrl(servletRequest), passwordResetToken);
    }
    return passwordResetUrl;
  }

  private String passwordResetEmailLink(User user, String applicationUrl,
                                        String passwordToken) throws MessagingException, UnsupportedEncodingException {
    String url = applicationUrl+"/register/reset-password?token="+passwordToken;
    registrationMailSender.sendVerificationEmail(user, url);
    return url;
  }

  public String applicationUrl(HttpServletRequest request) {
    return "http://"+request.getServerName()+":"
            +request.getServerPort()+request.getContextPath();
  }

  public String resetPassword(String token, String newPassword){
    String tokenVerificationResult = passwordResetTokenService.validatePasswordResetToken(token);
    if (!tokenVerificationResult.equalsIgnoreCase("valid")) {
      return "Invalid token password reset token";
    }
    Optional<User> theUser = (passwordResetTokenService.findUserByPasswordToken(token));
    if (theUser.isPresent()) {
      theUser.get().setPassword(passwordEncoder.encode(newPassword));
      userRepository.save(theUser.get());

      return "Password has been reset successfully";
    }
    return "Invalid password reset token";
  }
}
