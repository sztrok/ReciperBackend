package com.eat.it.eatit.backend.mapper.refactored.recipe;

import com.eat.it.eatit.backend.data.recipe.RecipeIngredient;
import com.eat.it.eatit.backend.dto.refactored.recipe.RecipeIngredientDTO;

import java.util.List;

public class RecipeIngredientMapper {

    private RecipeIngredientMapper() {
    }

    public static RecipeIngredientDTO toDTO(RecipeIngredient recipeIngredient) {
        RecipeIngredientDTO dto = new RecipeIngredientDTO();
        dto.setId(recipeIngredient.getId());
        dto.setName(recipeIngredient.getItem().getName());
        dto.setQuantity(recipeIngredient.getQuantity());
        dto.setUnit(recipeIngredient.getUnit().getUnit());
        dto.setCategory(recipeIngredient.getItem().getItemType().getDescription());
        dto.setQualities(recipeIngredient.getQualities());
        dto.setIsOptional(recipeIngredient.getIsOptional());
        return dto;
    }

    public static List<RecipeIngredientDTO> toDTOList(List<RecipeIngredient> recipeIngredients) {
        return recipeIngredients.stream().map(RecipeIngredientMapper::toDTO).toList();
    }
}
