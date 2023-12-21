package com.study.plus.comment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.study.plus.comment.dto.CommentRequestDto;
import com.study.plus.global.entity.BaseTimeEntity;
import com.study.plus.post.entity.Post;
import com.study.plus.security.UserDetailsImpl;
import com.study.plus.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  /**
   * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
   */
  @Column
  private String content;

  /**
   * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
   */
  public Comment(CommentRequestDto commentRequestDto, Post post, UserDetailsImpl userDetails) {
    this.content = commentRequestDto.getContent();
    this.post = post;
    this.user = userDetails.getUser();
  }

  /**
   * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
   */
  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "post_id")
  private Post post;

  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "user_id")
  private User user;

  /**
   * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
   */


  /**
   * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
   */
  public void updateComment(CommentRequestDto commentRequestDto) {
    this.content = commentRequestDto.getContent();
  }

}
