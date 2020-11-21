package org.picon.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Coordinate {
    @Column(name = "lat", columnDefinition = "decimal(19,7)")
    private BigDecimal lat;
    @Column(name = "lng", columnDefinition = "decimal(19,7)")
    private BigDecimal lng;
}
