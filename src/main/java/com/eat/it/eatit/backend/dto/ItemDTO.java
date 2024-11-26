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
    private Double fats;
    private Double carbs;
    private ItemType itemType;

    public ItemDTO(@Nullable Long id, String name, Long barcode, Double caloriesPer100g, Double proteins, Double fats, Double carbs, ItemType itemType) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.caloriesPer100g = caloriesPer100g;
        this.proteins = proteins;
        this.fats = fats;
        this.carbs = carbs;
        this.itemType = itemType;
    }

    public ItemDTO(String name, Long barcode, Double caloriesPer100g, Double proteins, Double fats, Double carbs) {
        this.name = name;
        this.barcode = barcode;
        this.caloriesPer100g = caloriesPer100g;
        this.proteins = proteins;
        this.fats = fats;
        this.carbs = carbs;
    }
}
