package com.eat.it.eatit.backend.item.data;

import com.eat.it.eatit.backend.fridge.data.FridgeDTO;
import com.eat.it.eatit.backend.recipe.data.RecipeDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class ItemDTO {

    private String name;
    private Long barcode;
    private Integer caloriesPer100g;
    private Double proteinPer100G;
    private Double fatPer100G;
    private Double carbsPer100G;
    private Double amount;
    private Set<RecipeDTO> recipesContainingItem;
    private Set<FridgeDTO> fridgesContainingItem;

    public ItemDTO(String name, Long barcode, Integer caloriesPer100g, Double proteinPer100G, Double fatPer100G, Double carbsPer100G) {
        this.name = name;
        this.barcode = barcode;
        this.caloriesPer100g = caloriesPer100g;
        this.proteinPer100G = proteinPer100G;
        this.fatPer100G = fatPer100G;
        this.carbsPer100G = carbsPer100G;
    }

    public ItemDTO(String name, Long barcode, Integer caloriesPer100g, Double proteinPer100G, Double fatPer100G, Double carbsPer100G, Double amount, Set<RecipeDTO> recipesContainingItem, Set<FridgeDTO> fridgesContainingItem) {
        this.name = name;
        this.barcode = barcode;
        this.caloriesPer100g = caloriesPer100g;
        this.proteinPer100G = proteinPer100G;
        this.fatPer100G = fatPer100G;
        this.carbsPer100G = carbsPer100G;
        this.amount = amount;
        this.recipesContainingItem = recipesContainingItem;
        this.fridgesContainingItem = fridgesContainingItem;
    }

    public ItemDTO(String name) {
        this.name = name;
    }
}
