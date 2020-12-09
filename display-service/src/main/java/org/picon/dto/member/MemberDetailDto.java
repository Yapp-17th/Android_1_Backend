package org.picon.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.picon.dto.BaseResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDetailDto extends BaseResponse{
    private MemberDto memberDto;
    private FollowInfo followInfo;
}
