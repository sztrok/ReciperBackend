package com.eat.it.eatit.backend.enums;

import lombok.Getter;

@Getter
public enum RecipeTags {

    VERY_EASY("Very easy recipe"),
    EASY("Easy recipe"),
    MEDIUM("Medium hard recipe"),
    HARD("Hard recipe"),
    VERY_HARD("Very hard recipe");

    private final String description;

    RecipeTags(String description) {
        this.description = description;
    }
}
