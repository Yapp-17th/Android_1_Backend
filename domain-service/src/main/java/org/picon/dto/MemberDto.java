package org.picon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.picon.domain.Member;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {
    private Long id;
    private String identity;
    private String nickName;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDate createdDate;
    private String role;
    private String profileImageUrl;
    private Boolean isFollowing;

    public static MemberDto fromForSearch(Member member, Member loginMember) {
        return MemberDto.builder()
                .id(member.getId())
                .identity(member.getIdentity())
                .nickName(member.getNickName())
                .createdDate(member.getCreateDate())
                .role(member.getRole())
                .profileImageUrl(member.getProfileImageUrl())
                .isFollowing(loginMember.isFollowing(member))
                .build();
    }
}
