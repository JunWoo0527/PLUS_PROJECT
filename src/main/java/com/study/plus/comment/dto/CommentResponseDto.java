package com.study.plus.comment.dto;

import com.study.plus.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {

  private Long id;
  private String content;
  private String nickname;
  private LocalDateTime createdBy;
  private LocalDateTime lastModifiedBy;

  public CommentResponseDto(Comment comment) {
    this.id = comment.getId();
    this.content = comment.getContent();
    this.nickname = comment.getUser().getNickname();
    this.createdBy = comment.getCreatedAt();
    this.lastModifiedBy = comment.getModifiedAt();
  }


}
