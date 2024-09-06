package com.eat.it.eatit.backend.dto;

import lombok.Data;

@Data
public class ItemDTO {
    private String name;
    private long barcode;
    private int caloriesPer100g;
    private int proteinPer100G;
    private int fatPer100G;
    private int carbsPer100G;

    public ItemDTO(String name, long barcode, int caloriesPer100g, int proteinPer100G, int fatPer100G, int carbsPer100G) {
        this.name = name;
        this.barcode = barcode;
        this.caloriesPer100g = caloriesPer100g;
        this.proteinPer100G = proteinPer100G;
        this.fatPer100G = fatPer100G;
        this.carbsPer100G = carbsPer100G;
    }
}
