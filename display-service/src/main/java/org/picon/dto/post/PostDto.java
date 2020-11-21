package org.picon.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class PostDto {
    private Long id;
    @Nullable
    private String profileImageUrl;
    @NotNull @Valid
    private CoordinateDto coordinate;
    @NotNull @Valid
    private AddressDto address;
    private List<String> imageUrls;
    @NotNull
    private Emotion emotion;
    @NotNull
    private String memo;
}
