package com.study.plus.comment.service;

import com.study.plus.comment.dto.CommentRequestDto;
import com.study.plus.comment.dto.CommentResponseDto;
import com.study.plus.comment.entity.Comment;
import com.study.plus.security.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

  Comment createComment(Long postId, CommentRequestDto commentRequestDto,
      UserDetailsImpl userDetails);

  Comment updateComment(Long postId, Long commentId, CommentRequestDto commentRequestDt,
      UserDetailsImpl userDetails);

  void deleteComment(Long postId, Long commentId,
      UserDetailsImpl userDetails);

  Page<CommentResponseDto> getComments(Long postId, Pageable pageable);
}
