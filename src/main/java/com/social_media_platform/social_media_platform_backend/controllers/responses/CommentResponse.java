package com.social_media_platform.social_media_platform_backend.controllers.responses;

import java.util.Date;
import java.util.Set;

import com.social_media_platform.social_media_platform_backend.databaseTables.Comment;
import com.social_media_platform.social_media_platform_backend.databaseTables.MainComment;
import com.social_media_platform.social_media_platform_backend.databaseTables.SubComment;

import lombok.Data;

@Data
public class CommentResponse {
    private Long commentId;
    private String commentContents;
    private Date commentDate;

    private Set<CommentResponse> replies;

    public CommentResponse(Comment comment) {
        if (comment instanceof MainComment) {
            new CommentResponse((MainComment) comment);
        } else {
            new CommentResponse((SubComment) comment);
        }
    }

    private CommentResponse(MainComment mainComment) {
        this.commentId = mainComment.getCommentId();
        this.commentContents = mainComment.getCommentContents();
        this.commentDate = mainComment.getCommentDate();

        for (var reply : mainComment.getSubComments()) {
            this.replies.add(new CommentResponse(reply));
        }
    }

    private CommentResponse(SubComment subComment) {
        this.commentId = subComment.getCommentId();
        this.commentContents = subComment.getCommentContents();
        this.commentDate = subComment.getCommentDate();
    }
}
