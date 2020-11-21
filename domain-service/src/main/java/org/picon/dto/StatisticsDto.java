package org.picon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDto {

    private EmotionCounts emotionCounts;
    private AddressCounts addressCounts;
}
