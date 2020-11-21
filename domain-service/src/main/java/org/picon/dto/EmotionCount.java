package org.picon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.picon.domain.Emotion;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmotionCount {
    private Emotion emotion;
    private Integer count;
}
