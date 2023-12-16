package com.study.plus.global.dto;

import com.study.plus.global.constant.ErrorCode;
import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Data
@Generated
@Getter
public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getHttpStatus().value();
        this.message = errorCode.getMessage();
    }

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(HttpStatus httpStatus, String message) {
        this.status = httpStatus.value();
        this.message = message;
    }
}

