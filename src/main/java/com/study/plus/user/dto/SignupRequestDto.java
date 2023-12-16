package com.study.plus.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{3,}$", message = "닉네임은 3자이상 영대소문자, 숫자만 가능합니다.")
    private String nickname;
    @NotBlank
    @Pattern(regexp = "^.{4,}$", message = "비밀번호는 4자 이상이여야합니다.")
    private String password;
    @NotBlank
    @Pattern(regexp = "^.{4,}$", message = "비밀번호 확인은 비밀번호와 같아야합니다.")
    private String passwordCheck;
    @NotBlank
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "이메일 형식에 맞게 작성해주세요.")
    private String email;
    @NotBlank
    @Pattern(regexp = "^[0-9]{6}$", message = "인증번호는 6자리 숫자입니다.")
    private String authCode;


}
