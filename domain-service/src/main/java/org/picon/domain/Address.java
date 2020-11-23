package org.picon.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Address {
    private String address;
    @EqualsAndHashCode.Include
    private String addrCity;
    private String addrDo;
    @EqualsAndHashCode.Include
    private String addrGu;
}
