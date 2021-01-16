package org.picon.auth.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LogInRequest {
    private String identity;
    private String password;
    private String token;

    public LogInRequest(String identity, String password) {
        this.identity = identity;
        this.password = password;
    }
}
