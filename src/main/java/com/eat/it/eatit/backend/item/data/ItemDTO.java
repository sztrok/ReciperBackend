package com.eat.it.eatit.backend.item.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDTO {

    private String name;
    private Long barcode;
    private Integer caloriesPer100g;
    private Double proteins;
    private Double fatPer100G;
    private Double carbsPer100G;
    private ItemType itemType;


    public ItemDTO(String name, Long barcode, Integer caloriesPer100g, Double proteins, Double fatPer100G, Double carbsPer100G, ItemType itemType) {
        this.name = name;
        this.barcode = barcode;
        this.caloriesPer100g = caloriesPer100g;
        this.proteins = proteins;
        this.fatPer100G = fatPer100G;
        this.carbsPer100G = carbsPer100G;
        this.itemType = itemType;
    }

    public ItemDTO(String name, Long barcode, Integer caloriesPer100g, Double proteins, Double fatPer100G, Double carbsPer100G) {
        this.name = name;
        this.barcode = barcode;
        this.caloriesPer100g = caloriesPer100g;
        this.proteins = proteins;
        this.fatPer100G = fatPer100G;
        this.carbsPer100G = carbsPer100G;
    }

    public ItemDTO(String name) {
        this.name = name;
    }

}
