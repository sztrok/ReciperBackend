package com.eat.it.eatit.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDTO {

    private String name;
    private Long barcode;
    private Integer caloriesPer100g;
    private Integer proteinPer100G;
    private Integer fatPer100G;
    private Integer carbsPer100G;

    public ItemDTO(String name, Long barcode, Integer caloriesPer100g, Integer proteinPer100G, Integer fatPer100G, Integer carbsPer100G) {
        this.name = name;
        this.barcode = barcode;
        this.caloriesPer100g = caloriesPer100g;
        this.proteinPer100G = proteinPer100G;
        this.fatPer100G = fatPer100G;
        this.carbsPer100G = carbsPer100G;
    }

    public ItemDTO(String name) {
        this.name = name;
    }
}
