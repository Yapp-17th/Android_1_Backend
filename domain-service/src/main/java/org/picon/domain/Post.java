package org.picon.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class Post {
    private Long id;
    private Coordinate coordinate;
    private Address address;
    private Emotion emotion;
    private String memo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;

    public static Post init(long id, Coordinate coordinate, Address address, Emotion emotion, String memo) {
        return new Post(id, coordinate, address, emotion, memo, LocalDate.now());
    }
}
