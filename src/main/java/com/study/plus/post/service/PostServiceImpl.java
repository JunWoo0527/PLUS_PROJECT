package com.study.plus.post.service;

import com.study.plus.post.dto.PostRequestDto;
import com.study.plus.post.dto.PostResponseDto;
import com.study.plus.post.entity.Likes;
import com.study.plus.post.entity.Post;
import com.study.plus.post.repository.LikesRepository;
import com.study.plus.post.repository.PostRepository;
import com.study.plus.security.UserDetailsImpl;
import com.study.plus.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final LikesRepository likesRepository;
//  private final UserServiceImpl userService;

  @Override
  public Post createPost(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
    String title = postRequestDto.getTitle();
    String content = postRequestDto.getContent();
    User user = userDetails.getUser();

    Post post = new Post(title, content, user);

    postRepository.save(post);
    return post;
  }

  @Override
  public Page<PostResponseDto> getPosts(Pageable pageable) {
    Page<Post> postList = postRepository.findAll(pageable);

    return postList.map(PostResponseDto::new);
  }

  @Override
  public PostResponseDto getPost(Long postId) {
    Post post = existPostInDBById(postId);
    return new PostResponseDto(post);
  }

  @Override
  @Transactional
  public void updatePost(Long postId, PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
    String userNickname = userDetails.getUsername();
    Post post = existPostInDBById(postId);

    if (!userNickname.equals(post.getUser().getNickname())) {
      throw new IllegalArgumentException("본인이 작성한 게시글만 수정 할 수 있습니다.");
    }

    post.update(postRequestDto);
    postRepository.save(post);
  }

  @Override
  @Transactional
  public void deletePost(Long postId, UserDetailsImpl userDetails) {
    String userNickname = userDetails.getUsername();
    Post post = existPostInDBById(postId);

    if (!userNickname.equals(post.getUser().getNickname())) {
      throw new IllegalArgumentException("본인이 작성한 게시글만 삭제 할 수 있습니다.");
    }

    postRepository.delete(post);
  }

  @Override
  public Post likePost(Long postId, UserDetailsImpl userDetails) {
    User user = userDetails.getUser();
    Post post = existPostInDBById(postId);

    if (user.getNickname().equals(post.getUser().getNickname())) {
      throw new IllegalArgumentException("본인이 작성한 게시글에는 좋아요를 할 수 없습니다.");
    }

    Likes likes = new Likes(post, user);
    likesRepository.save(likes);

    return post;
  }

  public Post existPostInDBById(Long postId) {
    Post post = postRepository.findById(postId).orElseThrow(() ->
        new IllegalArgumentException("해당 게시글이 존재하지않습니다.")
    );
    return post;
  }
}
