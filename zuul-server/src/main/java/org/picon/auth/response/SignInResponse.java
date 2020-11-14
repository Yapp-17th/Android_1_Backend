package org.picon.auth.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.picon.common.BaseResponse;

@Getter
@NoArgsConstructor
public class SignInResponse extends BaseResponse {
    private Long id;
    private String identity;
    private String nickName;

    @Builder
    public SignInResponse(Long id, String identity, String nickName) {
        this.id = id;
        this.identity = identity;
        this.nickName = nickName;
    }
}
