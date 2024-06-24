package com.social_media_platform.social_media_platform_backend.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.social_media_platform.social_media_platform_backend.databaseTables.User;
import com.social_media_platform.social_media_platform_backend.repositiries.UserRepository;

import java.util.List;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public void setUsername(Long userId, String newUsername) {
    userRepository.setUsername(userId, newUsername);
  }

  public User getUserInfo(Long userId) throws Exception {
    return userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
  }

  public void setPassword(Long userId, String oldPassword, String newPassword) throws Exception {
    var user = getUserInfo(userId);
    if (!user.getPassword().equals(passwordEncoder.encode(oldPassword))) {
      throw new Exception("Incorrect Passowrd");
    }
    userRepository.setPassword(userId, passwordEncoder.encode(newPassword));
  }

  public List<User> getNonBlockedUsers(String username, Long userId) {
    return userRepository.findUsersByUsernamePattern(username, userId);
  }
}
