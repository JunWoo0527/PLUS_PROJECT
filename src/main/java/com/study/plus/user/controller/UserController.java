package com.study.plus.user.controller;

import static com.study.plus.global.constant.ResponseCode.SUCCESS_POSTEMAIL;
import static com.study.plus.global.constant.ResponseCode.SUCCESS_SIGNUP;

import com.study.plus.global.dto.SuccessResponse;
import com.study.plus.user.dto.MailRequestDto;
import com.study.plus.user.dto.NicknameRequestDto;
import com.study.plus.user.dto.SignupRequestDto;
import com.study.plus.user.service.UserServiceImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

  private final UserServiceImpl userServiceImpl;

  @PostMapping("/user/signup-request")
  public ResponseEntity<SuccessResponse> signupRequest(
      @Valid @RequestBody MailRequestDto mailRequestDto,
      BindingResult bindingResult) {
    // Validation 예외처리
    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    if (fieldErrors.size() > 0) {
      for (FieldError fieldError : bindingResult.getFieldErrors()) {
        log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
      }
      return ResponseEntity.status(403).body(new SuccessResponse(403, "이메일 인증 오류"));
    }

    userServiceImpl.sendCodeToEmail(mailRequestDto);
    return ResponseEntity.ok().body(new SuccessResponse(SUCCESS_POSTEMAIL));
  }


  @PostMapping("/user/signup")
  public ResponseEntity<SuccessResponse> signup(
      @Valid @RequestBody SignupRequestDto signupRequestDto,
      BindingResult bindingResult) {
    // Validation 예외처리
    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    if (fieldErrors.size() > 0) {
      for (FieldError fieldError : bindingResult.getFieldErrors()) {
        log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
      }
      return ResponseEntity.status(403).body(new SuccessResponse(403, "회원가입오류"));
    }
    userServiceImpl.signup(signupRequestDto);
    return ResponseEntity.status(SUCCESS_SIGNUP.getHttpStatus())
        .body(new SuccessResponse(SUCCESS_SIGNUP));
  }

  @PostMapping("/user/nickname")
  public ResponseEntity<SuccessResponse> checkNickname(
      @RequestBody NicknameRequestDto nicknameRequestDto) {
    userServiceImpl.sameUserInDBByNickname(nicknameRequestDto.getNickname());
    return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK.value(), "사용가능한 닉네임입니다."));
  }
}
