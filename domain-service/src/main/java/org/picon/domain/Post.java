package org.picon.domain;

import lombok.*;
import org.picon.config.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "POST")
@Getter
@Builder
@ToString
public class Post extends BaseEntity {
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MEMBER_ID", referencedColumnName = "MEMBER_ID")
    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }
}
