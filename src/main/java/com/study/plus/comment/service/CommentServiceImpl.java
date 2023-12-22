package com.study.plus.comment.service;

import com.study.plus.comment.dto.CommentRequestDto;
import com.study.plus.comment.dto.CommentResponseDto;
import com.study.plus.comment.entity.Comment;
import com.study.plus.comment.repository.CommentRepository;
import com.study.plus.post.entity.Post;
import com.study.plus.post.service.PostServiceImpl;
import com.study.plus.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final PostServiceImpl postService;

  public Comment createComment(
      Long postId,
      CommentRequestDto commentRequestDto,
      UserDetailsImpl userDetails
  ) {
    Post post = postService.existPostInDBById(postId);
    Comment comment = new Comment(commentRequestDto, post, userDetails);

    commentRepository.save(comment);
    return comment;
  }

  @Override
  public Page<CommentResponseDto> getComments(
      Long postId,
      Pageable pageable
  ) {
    Post post = postService.existPostInDBById(postId);

    Page<Comment> commentList = commentRepository.findAllByPost(post, pageable);
    return commentList.map(CommentResponseDto::new);
  }

  @Override
  @Transactional
  public Comment updateComment(
      Long postId,
      Long commentId,
      CommentRequestDto commentRequestDto,
      UserDetailsImpl userDetails
  ) {
    postService.existPostInDBById(postId);
    Comment comment = existCommentInDBById(commentId);

    if (!userDetails.getUsername().equals(comment.getUser().getNickname())) {
      throw new IllegalArgumentException("댓글 작성자만 수정 할 수 있습니다.");
    }

    comment.updateComment(commentRequestDto);
    commentRepository.save(comment);

    return comment;
  }

  @Override
  public void deleteComment(
      Long postId,
      Long commentId,
      UserDetailsImpl userDetails
  ) {
    postService.existPostInDBById(postId);
    Comment comment = existCommentInDBById(commentId);

    if (!userDetails.getUsername().equals(comment.getUser().getNickname())) {
      throw new IllegalArgumentException("댓글 작성자만 삭제 할 수 있습니다.");
    }

    commentRepository.delete(comment);
  }

  public Comment existCommentInDBById(Long commentId) {
    Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
        new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
    );
    return comment;
  }
}
