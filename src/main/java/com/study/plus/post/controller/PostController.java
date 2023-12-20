package com.study.plus.post.controller;

import com.study.plus.global.constant.ResponseCode;
import com.study.plus.global.dto.SuccessResponse;
import com.study.plus.post.dto.PostRequestDto;
import com.study.plus.post.dto.PostResponseDto;
import com.study.plus.post.service.PostServiceImpl;
import com.study.plus.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

  private final PostServiceImpl postService;


  @PostMapping
  public ResponseEntity<SuccessResponse> createPost(
      @Valid @RequestBody PostRequestDto postRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    postService.createPost(postRequestDto, userDetails);

    return ResponseEntity.status(201).body(new SuccessResponse(ResponseCode.SUCCESS_CREATEPOST));

  }

  @GetMapping
  public ResponseEntity<Page<PostResponseDto>> getPosts(
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestParam("sortBy") String sortBy,
      @RequestParam("isAsc") boolean isAsc,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return ResponseEntity.ok()
        .body(postService.getPosts(page - 1, size, sortBy, isAsc, userDetails));
  }

  @GetMapping("/{postId}")
  public ResponseEntity<PostResponseDto> getPost(@PathVariable("postId") Long postId) {
    return ResponseEntity.ok().body(postService.getPost(postId));
  }

  @PutMapping("/{postId}")
  public ResponseEntity<SuccessResponse> updatePost(
      @PathVariable("postId") Long postId,
      @Valid @RequestBody PostRequestDto postRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    postService.updatePost(postId, postRequestDto, userDetails);
    return ResponseEntity.ok().body(new SuccessResponse(ResponseCode.SUCCESS_UPDATEPOST));
  }

  @DeleteMapping("/{postId}")
  public ResponseEntity<SuccessResponse> deletePost(@PathVariable("postId") Long postId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    postService.deletePost(postId, userDetails);
    return ResponseEntity.ok().body(new SuccessResponse(ResponseCode.SUCCESS_DELETEPOST));
  }
}
