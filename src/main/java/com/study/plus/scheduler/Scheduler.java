package com.study.plus.scheduler;


import com.study.plus.jwt.RefreshToken;
import com.study.plus.jwt.RefreshTokenRepository;
import com.study.plus.post.entity.Post;
import com.study.plus.post.repository.PostRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j(topic = "삭제 Scheduler")
@Component
@RequiredArgsConstructor
public class Scheduler {

  private final RefreshTokenRepository refreshTokenRepository;
  private final PostRepository postRepository;

  // 매시간 마다 만료된 토큰 자동삭제
  @Scheduled(cron = "0 0 * * * * ")
  public void logoutRefreshTokenCheck() {
    log.info("RefreshToken 삭제 실행");
    List<RefreshToken> expiredRefreshTokenList = refreshTokenRepository.findAll();
    if (!expiredRefreshTokenList.isEmpty()) {
      for (RefreshToken refreshToken : expiredRefreshTokenList) {
        LocalDateTime endTime = refreshToken.getCreatedAt().plusDays(7);
        if (LocalDateTime.now().isAfter(endTime)) {
          refreshTokenRepository.delete(refreshToken);
        }
      }
    }
  }

  @Scheduled(cron = "0 0 0 * * *")
  public void delete90dayPost() {
    log.info("90일 지난 게시글 삭제 실행");
    List<Post> postList = postRepository.findAll();
    if (!postList.isEmpty()) {
      for (Post post : postList) {
        LocalDateTime expireTime = post.getModifiedAt().plusDays(90);
        if (LocalDateTime.now().isAfter(expireTime)) {
          postRepository.delete(post);
        }
      }
    }
  }
}
