package com.study.plus.user.service;

import com.study.plus.global.constant.ErrorCode;
import com.study.plus.global.exception.ApiException;
import com.study.plus.mail.MailService;
import com.study.plus.redis.RedisService;
import com.study.plus.user.dto.MailRequestDto;
import com.study.plus.user.dto.SignupRequestDto;
import com.study.plus.user.entity.User;
import com.study.plus.user.entity.UserRoleEnum;
import com.study.plus.user.repository.UserRepository;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private static final String AUTH_CODE_PREFIX = "AuthCode ";

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final MailService mailService;

  private final RedisService redisService;

  // 만료시간 5분
  @Value("${spring.mail.auth-code-expiration-millis}")
  private Long authCodeExpirationMillis;

  @Override
  public void signup(SignupRequestDto signupRequestDto) {
    String nickname = signupRequestDto.getNickname();
    String password = signupRequestDto.getPassword();
    String passwordCheck = signupRequestDto.getPasswordCheck();
    String email = signupRequestDto.getEmail();
    String authCode = signupRequestDto.getAuthCode();

    // 이메일 인증검사
    EmailVerification(email, authCode);

    // 닉네임 중복검사
    sameUserInDBByNickname(signupRequestDto.getNickname());

    // 비밀번호 유효성 검사
    passwordAndNicknameCheck(nickname, password);

    // 비밀번호 확인검사
    passwordChecking(password, passwordCheck);

    // 비밀번호 암호화
    password = passwordEncoder.encode(password);

    // 새 유저 등록
    UserRoleEnum role = UserRoleEnum.USER;
    User user = new User(nickname, password, email, role);
    userRepository.save(user);

  }

  public void passwordChecking(String password, String passwordCheck) {
    if (!password.equals(passwordCheck)) {
      throw new IllegalArgumentException("비밀번호와 비밀번호확인이 같지않습니다. 다시 입력해주세요");
    }
  }

  public void passwordAndNicknameCheck(String nickname, String password) {
    if (password.contains(nickname)) {
      throw new IllegalArgumentException("닉네임을 비밀번호에 사용할수없습니다.");
    }
  }

  public void sameUserInDBByNickname(String nickname) {
    if (userRepository.existsUserByNickname(nickname)) {
      throw new IllegalArgumentException("중복된 닉네임입니다.");
    }
  }

  public void sameUserInDBByEmail(String email) {
    if (userRepository.existsUserByEmail(email)) {
      throw new IllegalArgumentException("이미 가입된 이메일이 존재합니다.");
    }
  }

  public void sendCodeToEmail(MailRequestDto mailRequestDto) {
    String email = mailRequestDto.getEmail();

    // email 중복검사
    sameUserInDBByEmail(email);

    String title = "회원가입 이메일 인증 번호";
    String authCode = createCode();
    mailService.sendEmail(email, title, authCode);
    // 이메일 인증 요청 시 인증 번호 Redis에 저장 ( key = "AuthCode " + Email / value = AuthCode )
    redisService.setValues(AUTH_CODE_PREFIX + email,
        authCode, Duration.ofMillis(authCodeExpirationMillis));
  }

  private String createCode() {
    int lenth = 6;
    try {
      Random random = SecureRandom.getInstanceStrong();
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < lenth; i++) {
        builder.append(random.nextInt(10));
      }
      return builder.toString();
    } catch (NoSuchAlgorithmException e) {
      log.debug("UserServiceImpl.createCode() exception occur");
      throw new ApiException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
  }

  private void EmailVerification(String email, String authCode) {
    sameUserInDBByEmail(email);
    String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + email);
    if (!(redisService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode))) {
      throw new IllegalArgumentException("인증번호가 틀렸습니다. 다시 입력해주세요.");
    } else {
      redisService.deleteValues(redisAuthCode);
    }
  }

}
