package com.eat.it.eatit.backend.dto.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeComponentDTO {
    private Long id;
    private String name;
    private List<RecipeIngredientDTO> ingredients = new ArrayList<>();

    public void setIngredients(List<RecipeIngredientDTO> ingredients) {
        this.ingredients = new ArrayList<>(ingredients);
    }
}
