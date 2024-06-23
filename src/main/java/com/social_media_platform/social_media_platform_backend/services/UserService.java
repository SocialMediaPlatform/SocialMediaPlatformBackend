package com.social_media_platform.social_media_platform_backend.services;

import org.springframework.stereotype.Service;

import com.social_media_platform.social_media_platform_backend.repositiries.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setUsername(Long userId, String newUsername) {
        userRepository.setUsername(userId, newUsername);
    }
}
