package org.picon.dto.member;

import lombok.*;
import org.picon.dto.BaseResponse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileRequest extends BaseResponse {

    private String profileUrl;
}
