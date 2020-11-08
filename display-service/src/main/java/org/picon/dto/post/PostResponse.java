package org.picon.dto.post;

import lombok.*;
import org.picon.dto.BaseResponse;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class PostResponse extends BaseResponse {
    private List<PostDto> posts;
}
