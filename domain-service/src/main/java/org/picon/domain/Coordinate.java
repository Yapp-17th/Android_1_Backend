package org.picon.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Coordinate {
    private double lat;
    private double lng;
}
