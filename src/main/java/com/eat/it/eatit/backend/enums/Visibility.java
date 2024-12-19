package com.eat.it.eatit.backend.enums;

import lombok.Getter;

@Getter
public enum Visibility {
    /**
     * Visible to everyone
     */
    PUBLIC("Public"),
    /**
     * Visible only to the owner.
     */
    PRIVATE("Private"),

    /**
     * Visible to friends of the owner.
     */
    FRIENDS("Friends"),

    /**
     * Visible to specific people it is shared with.
     */
    SHARED("Shared");

    private final String description;

    Visibility(String description) {
        this.description = description;
    }
}
