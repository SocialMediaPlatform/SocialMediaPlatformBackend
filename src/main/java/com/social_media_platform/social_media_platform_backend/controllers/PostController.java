package com.social_media_platform.social_media_platform_backend.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.social_media_platform.social_media_platform_backend.controllers.responses.PostResponse;
import com.social_media_platform.social_media_platform_backend.databaseTables.Post;
import com.social_media_platform.social_media_platform_backend.services.PostService;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("{userId}")
    public ResponseEntity<List<PostResponse>> getUserPosts(@PathVariable Long userId) {
        ArrayList<PostResponse> postResponses = new ArrayList<>();
        for (Post post : postService.getUserPosts(userId)) {
            postResponses.add(new PostResponse(post));
        }
        return new ResponseEntity<>(postResponses, HttpStatus.OK);
    }
}