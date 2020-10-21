package org.picon.auth.request;

import lombok.Getter;

@Getter
public class LogInRequest {
    private String email;
    private String password;
}
