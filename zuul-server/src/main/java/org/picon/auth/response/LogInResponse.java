package org.picon.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.picon.common.BaseResponse;

@Getter
@NoArgsConstructor
public class LogInResponse extends BaseResponse {
    private String accessToken;
    private String refreshToken;

    @Builder
    public LogInResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
