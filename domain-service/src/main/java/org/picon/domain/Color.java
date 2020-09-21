package org.picon.domain;

public enum Color {
    RED("별로"),
    ORANGE("상쾌함"),
    YELLOW("이상함"),
    GREEN("자연친화적"),
    BLUE("우울함"),
    BLACK("기본값");

    private final String emotion;

    Color(String emotion) {
        this.emotion = emotion;
    }
}

