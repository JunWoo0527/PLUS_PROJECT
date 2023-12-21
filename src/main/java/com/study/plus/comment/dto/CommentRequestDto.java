package com.study.plus.comment.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CommentRequestDto {

  @Length(max = 100)
  private String content;

}
