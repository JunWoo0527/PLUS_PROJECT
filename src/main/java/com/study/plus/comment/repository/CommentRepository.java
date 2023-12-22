package com.study.plus.comment.repository;

import com.study.plus.comment.entity.Comment;
import com.study.plus.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  Page<Comment> findAllByPost(Post post, Pageable pageable);

}
