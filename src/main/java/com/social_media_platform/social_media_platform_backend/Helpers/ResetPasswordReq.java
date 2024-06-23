package com.social_media_platform.social_media_platform_backend.Helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordReq {
    private String password;
    private String token;
}