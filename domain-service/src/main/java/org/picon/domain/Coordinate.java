package org.picon.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Coordinate {
    private BigDecimal lat;
    private BigDecimal lng;
}
