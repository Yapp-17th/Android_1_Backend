package org.picon.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "MEMBERS")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Member {

    @Id @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String role;
}
