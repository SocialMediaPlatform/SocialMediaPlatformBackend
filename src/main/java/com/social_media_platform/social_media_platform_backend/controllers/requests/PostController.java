package com.social_media_platform.social_media_platform_backend.controllers.requests;

import org.springframework.web.bind.annotation.*;

import com.social_media_platform.social_media_platform_backend.services.PostService;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
}
