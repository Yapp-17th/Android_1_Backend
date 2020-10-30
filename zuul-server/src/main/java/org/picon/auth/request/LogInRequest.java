package org.picon.auth.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LogInRequest {
    private String email;
    private String password;

    public LogInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
