package org.picon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.picon.domain.Coordinate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoordinateDto {
    private double lat;
    private double lng;

    public static CoordinateDto coordinateDtoOf(Coordinate coordinate) {
        return CoordinateDto.builder()
                .lat(coordinate.getLat())
                .lng(coordinate.getLng()).build();
    }
}
