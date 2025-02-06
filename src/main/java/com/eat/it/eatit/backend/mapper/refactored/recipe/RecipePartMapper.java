package com.eat.it.eatit.backend.mapper.refactored.recipe;

import com.eat.it.eatit.backend.data.refactored.recipe.RecipeComponent;
import com.eat.it.eatit.backend.dto.refactored.recipe.RecipePartDTO;

import java.util.List;

public class RecipePartMapper {

    private RecipePartMapper() {
    }

    public static RecipePartDTO toDTO(RecipeComponent recipeComponent) {
        RecipePartDTO dto = new RecipePartDTO();
        dto.setId(recipeComponent.getId());
        dto.setName(recipeComponent.getName());
        dto.setIngredients(RecipeIngredientMapper.toDTOList(recipeComponent.getRecipeIngredients()));
        return dto;
    }

    public static List<RecipePartDTO> toDTOList(List<RecipeComponent> recipeComponents) {
        return recipeComponents.stream().map(RecipePartMapper::toDTO).toList();
    }
}
