package org.picon.dto;

import lombok.*;
import org.picon.domain.Address;

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
