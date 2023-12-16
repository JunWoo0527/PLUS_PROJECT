package com.study.plus.global.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS_POSTEMAIL(OK, "이메일 인증코드를 전송하였습니다. 메일을 확인해주세요"),
    SUCCESS_REISSUANCETOKEN(OK, "토큰이 재발급되었습니다."),
    SUCCESS_SIGNUP(CREATED, "회원가입이 성공하였습니다,"),
    SUCCESS_LOGOUT(OK, "로그아웃이 성공하였습니다."),
    SUCCESS_EDITPROFILE(OK, "내정보 수정이 성공하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
