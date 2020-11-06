package org.picon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private Long id;
    private CoordinateDto coordinateDto;
    private AddressDto addressDto;
    private Emotion emotion;
    private List<String> imageUrls = new ArrayList<>();
    private String memo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;

    public static PostDto PostDtoOf(Long id, AddressDto addressDto, CoordinateDto coordinateDto, Emotion emotion, List<String> imageUrls, String memo, LocalDate createDate) {
        return PostDto.builder()
                .id(id)
                .addressDto(addressDto)
                .coordinateDto(coordinateDto)
                .emotion(emotion)
                .imageUrls(imageUrls)
                .memo(memo)
                .createDate(createDate).build();
    }
}
