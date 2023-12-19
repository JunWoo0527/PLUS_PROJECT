package com.study.plus.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
public class PostRequestDto {

  @Length(max = 500)
  private String title;
  @Length(max = 5000)
  private String content;

}
