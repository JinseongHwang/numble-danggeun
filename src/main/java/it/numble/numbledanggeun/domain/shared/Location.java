package it.numble.numbledanggeun.domain.shared;

import lombok.Getter;

@Getter
public enum Location {

    GASAN_DIGITAL_COMPLEX("가산디지털단지");

    private final String korLocation;

    Location(String korLocation) {
        this.korLocation = korLocation;
    }
}
