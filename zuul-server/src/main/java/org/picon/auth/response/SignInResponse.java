package org.picon.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.picon.common.BaseResponse;

@Getter
@NoArgsConstructor
public class SignInResponse extends BaseResponse {
    private Long id;
    private String email;

    @Builder
    public SignInResponse(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
