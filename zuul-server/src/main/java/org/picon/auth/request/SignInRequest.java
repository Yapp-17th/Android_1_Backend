package org.picon.auth.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInRequest {
    private String email;
    private String password;
    private String role;

    public SignInRequest(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
