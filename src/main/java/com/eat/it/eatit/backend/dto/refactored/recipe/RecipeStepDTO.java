package com.eat.it.eatit.backend.dto.refactored.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeStepDTO {
    private Long id;
    private String description;
    private List<RecipeIngredientDTO> ingredients = new ArrayList<>();

    public void setIngredients(List<RecipeIngredientDTO> ingredients) {
        this.ingredients = new ArrayList<>(ingredients);
    }
}
