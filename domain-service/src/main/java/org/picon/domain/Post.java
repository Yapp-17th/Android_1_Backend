package org.picon.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Post {
    @Id @GeneratedValue
    private Long id;
    private Coordinate coordinate;
    private Address address;
    @Enumerated(EnumType.STRING)
    private Emotion emotion;
    private String memo;
    @ElementCollection
    @CollectionTable(
            name="POST_IMAGES",
            joinColumns = @JoinColumn(name="POST_ID")
    )
    private Set<Image> images = new HashSet<>();
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;

    public static Post of(Coordinate coordinate, Address address, Emotion emotion, Set<Image> images, String memo) {
        return new Post(null, coordinate, address, emotion, memo,images,  LocalDate.now());
    }
}
