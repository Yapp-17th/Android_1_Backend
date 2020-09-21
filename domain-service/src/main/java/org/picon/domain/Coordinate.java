package org.picon.domain;

import lombok.Getter;

@Getter
public class Coordinate {
    private final double lat;
    private final double lng;

    public Coordinate(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override public String toString() {
        return "Coordinate{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
