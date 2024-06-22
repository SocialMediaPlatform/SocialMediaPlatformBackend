package com.social_media_platform.social_media_platform_backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social_media_platform.social_media_platform_backend.controllers.requests.SetUserRelationRequest;
import com.social_media_platform.social_media_platform_backend.services.UserRelationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/userRelation")
public class UserRelationController {
    private UserRelationService userRelationService;

    public UserRelationController(UserRelationService userRelationService) {
        this.userRelationService = userRelationService;
    }

    @PostMapping("set")
    public ResponseEntity<?> setUserRelation(@RequestBody SetUserRelationRequest setUserRelationRequest) {
        try {
            userRelationService.setUserRelation(setUserRelationRequest.getUserId(),
                    setUserRelationRequest.getTargetUserId(),
                    setUserRelationRequest.getRelationTypeId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
