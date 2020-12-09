package org.picon.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FollowInfo {
    private int followers;
    private int followings;
}
