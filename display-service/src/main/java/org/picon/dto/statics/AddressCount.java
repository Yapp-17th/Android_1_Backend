package org.picon.dto.statics;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressCount {
    private String addrCity;
    private String addrGu;
    private List<EmotionCount> emotionCounts;
    private int total;

}
