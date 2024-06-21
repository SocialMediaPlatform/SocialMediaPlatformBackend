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
  private Long mainCommentId;

  private Set<CommentResponse> replies;

  public CommentResponse() {}

  public CommentResponse(Comment comment) {
    this.setCommentId(comment.getCommentId());
    this.setCommentContents(comment.getCommentContents());
    this.setCommentDate(comment.getCommentDate());
    if (comment instanceof MainComment) {
      for (var reply : ((MainComment) comment).getSubComments()) {
        this.replies.add(new CommentResponse(reply));
      }
    } else {
      this.setMainCommentId(((SubComment) comment).getMainComment().getCommentId());
    }
  }

  private CommentResponse(SubComment comment) {
    this.setCommentId(comment.getCommentId());
    this.setCommentContents(comment.getCommentContents());
    this.setCommentDate(comment.getCommentDate());
    this.setMainCommentId(comment.getMainComment().getCommentId());
  }
}
