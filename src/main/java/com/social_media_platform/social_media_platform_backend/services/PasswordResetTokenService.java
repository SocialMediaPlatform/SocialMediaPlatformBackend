package com.social_media_platform.social_media_platform_backend.services;

import com.social_media_platform.social_media_platform_backend.databaseTables.PasswordResetToken;
import com.social_media_platform.social_media_platform_backend.databaseTables.User;
import com.social_media_platform.social_media_platform_backend.repositiries.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.Optional;

@Service
public class PasswordResetTokenService {
  private final PasswordResetTokenRepository passwordResetTokenRepository;

  @Value("${mail.tokenExpiration}")
  private int mailTokenExpiration;

  public PasswordResetTokenService(PasswordResetTokenRepository passwordResetTokenRepository) {
    this.passwordResetTokenRepository = passwordResetTokenRepository;
  }

  public void createPasswordResetTokenForUser(User user, String passwordToken) {
    Optional<PasswordResetToken> existingToken = passwordResetTokenRepository.findByUser(user);
    existingToken.ifPresent(passwordResetTokenRepository::delete);

    PasswordResetToken passwordResetToken =
        new PasswordResetToken(passwordToken, user, mailTokenExpiration);
    passwordResetTokenRepository.save(passwordResetToken);
  }

  public String validatePasswordResetToken(String passwordResetToken) {
    PasswordResetToken passwordToken = passwordResetTokenRepository.findByToken(passwordResetToken);
    if (passwordToken == null) {
      return "Invalid verification token";
    }
    User user = passwordToken.getUser();
    Calendar calendar = Calendar.getInstance();
    if ((passwordToken.getExpirationDate().getTime() - calendar.getTime().getTime()) <= 0) {
      return "Link already expired, resend link";
    }
    return "valid";
  }

  public Optional<User> findUserByPasswordToken(String passwordResetToken) {
    return Optional.ofNullable(
        passwordResetTokenRepository.findByToken(passwordResetToken).getUser());
  }

  public PasswordResetToken findTokenByStringToken(String token) {
    return passwordResetTokenRepository.findByToken(token);
  }
}
