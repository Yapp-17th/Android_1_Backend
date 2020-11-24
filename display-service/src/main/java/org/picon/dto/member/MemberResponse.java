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
public class MemberResponse extends BaseResponse {
    private MemberDto member;
}
