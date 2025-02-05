package com.eat.it.eatit.backend.dto.refactored.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredientDTO {
    private Long id;
    private String name;
    private List<String> alternativeIngredients = new ArrayList<>();
    private Double quantity;
    private String unit;
    private String category;
    private List<String> qualities = new ArrayList<>();
    private Boolean isOptional = false;

    public void setAlternativeIngredients(List<String> alternativeIngredients) {
        this.alternativeIngredients = new ArrayList<>(alternativeIngredients);
    }

    public void setQualities(List<String> qualities) {
        this.qualities = new ArrayList<>(qualities);
    }
}
