package com.eat.it.eatit.backend.enums;

import lombok.Getter;

@Getter
public enum ResponseCookieType {
    ACCESS_TOKEN("accessToken"),
    REFRESH_TOKEN("refreshToken");

    private final String description;

    ResponseCookieType(String description) {
        this.description = description;
    }
}
