package com.study.plus.post.dto;

import com.study.plus.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponseDto {

  private Long id;
  private String title;
  private String content;
  private String nickname;

  public PostResponseDto(Post post) {
    this.id = post.getId();
    this.title = post.getTitle();
    this.content = post.getContent();
    this.nickname = post.getUser().getNickname();
  }
}
