package com.study.plus.comment.controller;

import com.study.plus.comment.dto.CommentRequestDto;
import com.study.plus.comment.dto.CommentResponseDto;
import com.study.plus.comment.entity.Comment;
import com.study.plus.comment.service.CommentServiceImpl;
import com.study.plus.global.constant.ResponseCode;
import com.study.plus.global.dto.SuccessResponse;
import com.study.plus.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

  private final CommentServiceImpl commentService;

  @PostMapping("/{postId}")
  public ResponseEntity<SuccessResponse> createComment(
      @PathVariable("postId") Long postId,
      @RequestBody CommentRequestDto commentRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    Comment comment = commentService.createComment(postId, commentRequestDto, userDetails);
    return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(
        ResponseCode.SUCCESS_CREATECOMMENT, new CommentResponseDto(comment)));
  }

  @PutMapping("/{postId}")
  public ResponseEntity<SuccessResponse> updateComment(
      @PathVariable("postId") Long postId,
      @RequestParam("commentId") Long commentId,
      @RequestBody CommentRequestDto commentRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    Comment comment = commentService.updateComment(postId, commentId, commentRequestDto,
        userDetails);
    return ResponseEntity.ok().body(
        new SuccessResponse(ResponseCode.SUCCESS_UPDATECOMMENT, new CommentResponseDto(comment)));
  }

  @DeleteMapping("/{postId}")
  public ResponseEntity<SuccessResponse> deleteComment(
      @PathVariable("postId") Long postId,
      @RequestParam("commentId") Long commentId,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    commentService.deleteComment(postId, commentId, userDetails);
    return ResponseEntity.ok().body(new SuccessResponse(ResponseCode.SUCCESS_DELETECOMMENT));
  }
}
