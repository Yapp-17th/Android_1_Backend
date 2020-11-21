package org.picon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDto {

    private List<EmotionCount> emotionCounts;
    private int emotionTotal;
    private List<AddressCount> addressCounts;
}
