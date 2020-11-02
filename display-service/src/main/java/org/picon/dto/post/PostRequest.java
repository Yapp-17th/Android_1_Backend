package org.picon.dto.post;

import lombok.*;
import org.picon.dto.BaseRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class PostRequest extends BaseRequest {
    private PostDto post;
}
