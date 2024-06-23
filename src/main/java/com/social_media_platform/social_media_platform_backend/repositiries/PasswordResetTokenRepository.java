package com.social_media_platform.social_media_platform_backend.repositiries;

import com.social_media_platform.social_media_platform_backend.databaseTables.PasswordResetToken;
import com.social_media_platform.social_media_platform_backend.databaseTables.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
  PasswordResetToken findByToken(String passwordResetToken);

  Optional<PasswordResetToken> findByUser(User user);
}
