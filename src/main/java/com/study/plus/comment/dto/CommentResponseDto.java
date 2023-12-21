package com.study.plus.comment.dto;

import com.study.plus.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommentResponseDto {

  private String content;
  private String nickname;
  private LocalDateTime createdBy;
  private LocalDateTime lastModifiedBy;

  public CommentResponseDto(Comment comment) {
    this.content = comment.getContent();
    this.nickname = comment.getUser().getNickname();
    this.createdBy = comment.getCreatedAt();
    this.lastModifiedBy = comment.getModifiedAt();
  }


}
