package org.picon.domain;

import lombok.Getter;

@Getter
public enum Emotion {
    SOFT_BLUE("#8187DA"),
    CORN_FLOWER("#6699FC"),
    BLUE_GRAY("#79AED0"),
    VERY_LIGHT_BROWN("#E6AF75"),
    WARM_GRAY("#9A948B");

    private final String rgb;

    Emotion(String rgb) {
        this.rgb = rgb;
    }
}
