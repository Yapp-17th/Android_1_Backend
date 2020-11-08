package org.picon.auth.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.picon.common.BaseResponse;

@Getter
@NoArgsConstructor
public class AccessTokenResponse extends BaseResponse {
    private String accessToken;

    @Builder
    public AccessTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
