package com.study.plus.user.service;

import com.study.plus.user.dto.SignupRequestDto;

public interface UserService {

    void signup(SignupRequestDto signupRequestDto);

    static void passwordChecking(SignupRequestDto signupRequestDto) {
        String password = signupRequestDto.getPassword();
        String passwordCheck = signupRequestDto.getPasswordCheck();

        if (!password.equals(passwordCheck)) {
            throw new IllegalArgumentException("비밀번호와 비밀번호확인 같지않습니다. 다시 입력해주세요");
        }
    }
}
