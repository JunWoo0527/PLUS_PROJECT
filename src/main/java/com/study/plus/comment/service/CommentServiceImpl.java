package com.study.plus.comment.service;

import com.study.plus.comment.dto.CommentRequestDto;
import com.study.plus.comment.dto.CommentResponseDto;
import com.study.plus.comment.entity.Comment;
import com.study.plus.comment.repository.CommentRepository;
import com.study.plus.post.entity.Post;
import com.study.plus.post.service.PostServiceImpl;
import com.study.plus.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final PostServiceImpl postService;

  public CommentResponseDto createComment(
      Long postId,
      CommentRequestDto commentRequestDto,
      UserDetailsImpl userDetails
  ) {

    Post post = postService.existPostInDBById(postId);
    Comment comment = new Comment(commentRequestDto, post, userDetails);

    commentRepository.save(comment);
    return new CommentResponseDto(comment);
  }

  
}
