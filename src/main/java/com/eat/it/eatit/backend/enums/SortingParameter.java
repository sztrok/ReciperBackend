package com.eat.it.eatit.backend.enums;

import lombok.Getter;

@Getter
public enum SortingParameter {
    NUM_OF_LIKES("sort by number of likes"),
    NUM_OF_AVAILABLE_INGREDIENTS("sort by number of available ingredients");

    private final String description;

    SortingParameter(String description) {
        this.description = description;
    }
}
