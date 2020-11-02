package org.picon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private Long id;
    private CoordinateDto coordinateDto;
    private AddressDto addressDto;
    private Emotion emotion;
    private ImageDto imageDto;
    private String memo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;
}
