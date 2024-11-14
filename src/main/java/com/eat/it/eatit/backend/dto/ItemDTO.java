package com.eat.it.eatit.backend.dto;

import com.eat.it.eatit.backend.enums.ItemType;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDTO {

    @Nullable
    private Long id;
    private String name;
    private Long barcode;
    private Double caloriesPer100g;
    private Double proteins;
    private Double fatPer100G;
    private Double carbsPer100G;
    private ItemType itemType;

    public ItemDTO(@Nullable Long id, String name, Long barcode, Double caloriesPer100g, Double proteins, Double fatPer100G, Double carbsPer100G, ItemType itemType) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.caloriesPer100g = caloriesPer100g;
        this.proteins = proteins;
        this.fatPer100G = fatPer100G;
        this.carbsPer100G = carbsPer100G;
        this.itemType = itemType;
    }

    public ItemDTO(String name, Long barcode, Double caloriesPer100g, Double proteins, Double fatPer100G, Double carbsPer100G) {
        this.name = name;
        this.barcode = barcode;
        this.caloriesPer100g = caloriesPer100g;
        this.proteins = proteins;
        this.fatPer100G = fatPer100G;
        this.carbsPer100G = carbsPer100G;
    }
}
