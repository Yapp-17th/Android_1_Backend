package org.picon.auth.request;

import lombok.Getter;

@Getter
public class SignInRequest {
    private String email;
    private String password;
    private String role;

}
