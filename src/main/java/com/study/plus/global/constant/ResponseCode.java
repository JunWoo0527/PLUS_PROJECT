package com.study.plus.global.constant;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {
  SUCCESS_POSTEMAIL(OK, "이메일 인증코드를 전송하였습니다. 메일을 확인해주세요"),
  SUCCESS_REISSUANCETOKEN(OK, "토큰이 재발급되었습니다."),
  SUCCESS_SIGNUP(CREATED, "회원가입이 성공하였습니다,"),
  SUCCESS_LOGOUT(OK, "로그아웃이 성공하였습니다."),
  SUCCESS_LIKEPOST(OK, "게시글에 좋아요가 성공하였습니다"),

  SUCCESS_CREATEPOST(CREATED, "게시글이 게시가 성공하였습니다."),
  SUCCESS_DELETEPOST(OK, "게시글이 삭제되었습니다."),
  SUCCESS_UPDATEPOST(OK, "게시글의 내용이 수정되었습니다."),

  SUCCESS_CREATECOMMENT(CREATED, "댓글이 게시가 성공하였습니다."),
  SUCCESS_UPDATECOMMENT(OK, "댓글이 수정되었습니다."),
  SUCCESS_DELETECOMMENT(OK, "댓글이 삭제되었습니다."),

  SUCCESS_UPLOADIMAGE(OK, "이미지가 업로드되었습니다."),

  SUCCESS_EDITPROFILE(OK, "내정보 수정이 성공하였습니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
