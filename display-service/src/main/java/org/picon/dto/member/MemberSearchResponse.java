package org.picon.dto.member;

import lombok.*;
import org.picon.dto.BaseResponse;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberSearchResponse extends BaseResponse {
    private List<MemberDto> members;
    private FollowInfo followInfo;

}
