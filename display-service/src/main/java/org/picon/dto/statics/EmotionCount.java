package org.picon.dto.statics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.picon.dto.post.Emotion;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmotionCount {
    private Emotion emotion;
    private Integer count;
}
