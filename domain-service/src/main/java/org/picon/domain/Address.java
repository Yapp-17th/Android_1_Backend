package org.picon.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@ToString
@Builder
public class Address {
    private String address;
    private String addrCity;
    private String addrDo;
    private String addrGu;
}
