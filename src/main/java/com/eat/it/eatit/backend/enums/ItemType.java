package com.eat.it.eatit.backend.enums;

import lombok.Getter;

@Getter
public enum ItemType {
    VEGETABLE("Vegetable"),
    FRUIT("Fruit"),
    DAIRY("Dairy"),
    BEEF("Beef"),
    PORK("Pork"),
    FISH("Fish"),
    SEAFOOD("SeaFood"),
    POULTRY("Poultry"),
    GRAIN("Grain"),
    LEGUME("Legume"),
    NUTS_AND_SEEDS("Nuts and seeds"),
    FATS_AND_OILS("Fats and Oils"),
    SWEETS("Sweets"),
    BEVERAGE("Beverage");

    private final String description;

    ItemType(String description) {
        this.description = description;
    }

}
