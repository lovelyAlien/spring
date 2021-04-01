package com.sparta.project02.dto;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class SignupRequestDto {

//    @Size(min=3, message="닉네임을 최소 3자 이상 입력해주세요.")
//    @Pattern(regexp="[a-zA-A1-9]", message="닉네임을 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 입력해주세요.")
    private String username;

//    @Size(min=4, message="닉네임을 최소 4자 이상 입력해주세요.")
    private String password;
    private String confirm;
//    @NotBlank(message = "이메일은 필수 입력 값입니다.")
//    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    private boolean admin = false;
    private String adminToken = "";
}
