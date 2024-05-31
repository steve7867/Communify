package com.communify.domain.auth.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class LoginRequest {

    @Email(message = "email 형식으로 입력해주세요.")
    @NotBlank(message = "공백은 허용되지 않습니다.")
    @Size(max = 50, message = "최대 {0}자까지 허용됩니다.")
    private final String email;

    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()-_+=<>?/{}|~]{7,20}$",
            message = "숫자, 영문 대소문자, 공백을 제외한 특수 문자 7 ~ 20자리를 입력해주세요.")
    @NotBlank(message = "공백은 허용되지 않습니다.")
    private final String password;
}