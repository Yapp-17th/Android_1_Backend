package org.picon.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Posts")
@Getter
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Builder
    public Post(Coordinate coordinate, Address address, Emotion emotion, String memo, Set<Image> images, LocalDate createDate) {
        this.coordinate = coordinate;
        this.address = address;
        this.emotion = emotion;
        this.memo = memo;
        this.images = images;
        this.createDate = createDate;
    }

    public static Post of(Coordinate coordinate, Address address, Emotion emotion, Set<Image> images, String memo) {
        return Post.builder()
                .coordinate(coordinate)
                .address(address)
                .emotion(emotion)
                .memo(memo)
                .images(images)
                .createDate(LocalDate.now()).build();
    }
}
