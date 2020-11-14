package org.picon.auth.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class SignInRequest {
    @NotBlank(message = "아이디 필수 입력란입니다")
    private String identity;
    @NotNull @Size(min = 6, max = 14, message = "비밀번호는 6자리 이상 14자리 이하여야 합니다")
    private String password;
    @NotNull @Size(min = 3, message = "닉네임은 최소 3글자 이상이여야 합니다")
    private String nickName;

    public SignInRequest(String identity, String password, String nickName) {
        this.identity = identity;
        this.password = password;
        this.nickName = nickName;
    }
}
