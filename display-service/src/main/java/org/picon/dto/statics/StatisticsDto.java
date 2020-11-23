package org.picon.dto.statics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.picon.dto.BaseResponse;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDto extends BaseResponse {

    private List<EmotionCount> emotionCounts;
    private int emotionTotal;
    private List<AddressCount> addressCounts;
}
