package com.social_media_platform.social_media_platform_backend.controllers.responses;

import java.util.Date;
import java.util.List;

import com.social_media_platform.social_media_platform_backend.databaseTables.Comment;
import com.social_media_platform.social_media_platform_backend.databaseTables.SubComment;

import lombok.Data;

@Data
public class CommentResponse {
  private Long commentId;
  private String commentContents;
  private Date commentDate;
  private Long mainCommentId;

  private List<CommentResponse> replies;

  public CommentResponse() {}

  public CommentResponse(Comment comment) {
    this.setCommentId(comment.getCommentId());
    this.setCommentContents(comment.getCommentContents());
    this.setCommentDate(comment.getCommentDate());
    if (comment instanceof SubComment) {
      this.setMainCommentId(((SubComment) comment).getMainComment().getCommentId());
    }
  }
}
