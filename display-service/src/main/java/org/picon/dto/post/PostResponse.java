package org.picon.dto.post;

import lombok.*;
import org.picon.dto.BaseResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class PostResponse extends BaseResponse {
    private PostDto post;
}
