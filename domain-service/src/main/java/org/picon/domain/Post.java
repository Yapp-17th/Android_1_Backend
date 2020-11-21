package org.picon.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "POST")
@Getter
@Builder
@ToString
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;
    private Coordinate coordinate;
    private Address address;
    @Convert(converter = ImageUrlsConverter.class)
    private List<String> imageUrls;
    @Enumerated(EnumType.STRING)
    private Emotion emotion;
    private String memo;
    @CreatedDate
    private LocalDate createDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MEMBER_ID", referencedColumnName = "MEMBER_ID")
    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }
}
