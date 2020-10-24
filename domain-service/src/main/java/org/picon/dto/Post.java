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
public class Post {
    private Long id;
    private Coordinate coordinate;
    private Address address;
    private Emotion emotion;
    private String memo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;
}
