package com.study.plus.post.service;

import com.study.plus.post.dto.PostRequestDto;
import com.study.plus.post.dto.PostResponseDto;
import com.study.plus.security.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostService {

  void createPost(PostRequestDto postRequestDto, UserDetailsImpl userDetails);

  Page<PostResponseDto> getPosts(Pageable pageable);

  PostResponseDto getPost(Long postid);

  void updatePost(Long postId, PostRequestDto postRequestDto, UserDetailsImpl userDetails);

  void deletePost(Long postId, UserDetailsImpl userDetails);
}
