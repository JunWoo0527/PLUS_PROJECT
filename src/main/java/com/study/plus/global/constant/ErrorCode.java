package com.study.plus.global.constant;

import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@Generated
public enum ErrorCode {
    // Token Exception
    UNKNOWN_ERROR_NOT_EXIST_REFRESHTOKEN(HttpStatus.NOT_FOUND, "리프레시 토큰이 존재하지 않습니다. 다시 로그인해주세요."),

    UNKNOWN_ERROR_NOT_EXIST_ACCESSTOKEN(HttpStatus.NOT_FOUND, "액세스 토큰이 존재하지 않습니다. 다시 로그인해주세요."),

    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, "만료된 토큰입니다. 다시 로그인하세요."),

    ACCESS_DENIED(HttpStatus.BAD_REQUEST, "유효하지 못한 토큰입니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러"),

    INVALID_VALUE(HttpStatus.BAD_REQUEST,"잘못된 입력값입니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
