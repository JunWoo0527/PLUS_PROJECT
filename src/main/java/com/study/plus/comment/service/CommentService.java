package com.study.plus.comment.service;

import com.study.plus.comment.dto.CommentRequestDto;
import com.study.plus.comment.dto.CommentResponseDto;
import com.study.plus.security.UserDetailsImpl;

public interface CommentService {

  CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto,
      UserDetailsImpl userDetails);

}
