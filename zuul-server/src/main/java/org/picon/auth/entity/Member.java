package org.picon.auth.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Table(name = "MEMBERS")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Member extends BaseEntity{

    @Id @Column(name = "MEMBER_ID", nullable = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identity;
    private String password;
    private String nickName;
    private String role;
    private String token;

}
