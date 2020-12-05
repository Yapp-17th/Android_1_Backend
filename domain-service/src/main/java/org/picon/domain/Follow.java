package org.picon.domain;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.picon.config.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "FOLLOW")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Builder
public class Follow extends BaseEntity {
    @Id @Column(name = "FOLLOW_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", referencedColumnName = "MEMBER_ID")
    @EqualsAndHashCode.Include
    @NaturalId
    protected Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOLLOW_MEMBER_ID", referencedColumnName = "MEMBER_ID")
    @EqualsAndHashCode.Include
    @NaturalId
    protected Member followMember;

    public static Follow of(Member member, Member followMember) {
        return new Follow(null, member, followMember);
    }
}
