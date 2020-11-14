package org.picon.auth.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LogInRequest {
    private String identity;
    private String password;

    public LogInRequest(String identity, String password) {
        this.identity = identity;
        this.password = password;
    }
}
