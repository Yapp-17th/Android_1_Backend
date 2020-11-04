package org.picon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.picon.domain.Coordinate;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoordinateDto {
    private BigDecimal lat;
    private BigDecimal lng;
}
