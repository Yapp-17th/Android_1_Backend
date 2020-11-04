package org.picon.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostDto {
    private Long id;
    private CoordinateDto coordinate;
    private AddressDto address;
    private Emotion emotion;
    private String memo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;
}
