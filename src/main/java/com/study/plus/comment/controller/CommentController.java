package com.study.plus.comment.controller;

import com.study.plus.comment.dto.CommentRequestDto;
import com.study.plus.comment.dto.CommentResponseDto;
import com.study.plus.comment.service.CommentServiceImpl;
import com.study.plus.global.constant.ResponseCode;
import com.study.plus.global.dto.SuccessResponse;
import com.study.plus.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

  private final CommentServiceImpl commentService;

  @PostMapping("{postId}")
  public ResponseEntity<SuccessResponse> createComment(
      @PathVariable("postId") Long postId,
      CommentRequestDto commentRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    CommentResponseDto commentResponseDto = commentService.createComment(postId, commentRequestDto,
        userDetails);
    return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(
        ResponseCode.SUCCESS_CREATECOMMENT, commentResponseDto));
  }
}
