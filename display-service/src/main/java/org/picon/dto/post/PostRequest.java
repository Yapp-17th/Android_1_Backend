package org.picon.dto.post;

import lombok.*;
import org.picon.dto.BaseRequest;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class PostRequest extends BaseRequest {
    @NotNull @Valid
    private PostDto post;
}
