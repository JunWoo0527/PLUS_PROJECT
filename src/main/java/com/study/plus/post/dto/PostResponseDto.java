package com.study.plus.post.dto;

import com.study.plus.comment.entity.Comment;
import com.study.plus.post.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponseDto {

  private Long id;
  private String title;
  private String content;
  private String nickname;
  private Long likes;
  private LocalDateTime createdBy;
  private LocalDateTime lastModifiedBy;
  private List<Comment> comments;

  public PostResponseDto(Post post) {
    this.id = post.getId();
    this.title = post.getTitle();
    this.content = post.getContent();
    this.nickname = post.getUser().getNickname();
    this.likes = post.getLikesList().stream().count();
    this.createdBy = post.getCreatedAt();
    this.lastModifiedBy = post.getModifiedAt();
    this.comments = post.getComments();
  }
}
