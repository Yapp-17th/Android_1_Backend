package org.picon.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDetailDto {
    private  MemberDto memberDto;
    private  FollowInfo followInfo;
}
