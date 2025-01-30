package com.eat.it.eatit.backend.enums;

import lombok.Getter;

@Getter
public enum UnitOfMeasure {
    GRAM("g"),
    TABLE_SPOON("table spoon"),
    KILOGRAM("kg");

    private final String unit;
    UnitOfMeasure(String unit) {
        this.unit = unit;
    }
}
