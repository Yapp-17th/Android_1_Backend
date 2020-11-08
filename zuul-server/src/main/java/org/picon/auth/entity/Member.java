package org.picon.auth.entity;

import lombok.*;
import org.checkerframework.checker.units.qual.A;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "MEMBERS")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Member {

    @Id @Column(name = "MEMBER_ID", nullable = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String role;

//    @Builder
//    public Member(String email, String password, String role) {
//        this.email = email;
//        this.password = password;
//        this.role = role;
//    }
}
