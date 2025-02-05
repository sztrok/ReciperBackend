package com.eat.it.eatit.backend.mapper.refactored.recipe;

import com.eat.it.eatit.backend.data.refactored.recipe.RecipePart;
import com.eat.it.eatit.backend.dto.refactored.recipe.RecipePartDTO;

import java.util.List;

public class RecipePartMapper {

    private RecipePartMapper() {
    }

    public static RecipePartDTO toDTO(RecipePart recipePart) {
        RecipePartDTO dto = new RecipePartDTO();
        dto.setId(recipePart.getId());
        dto.setName(recipePart.getName());
        dto.setIngredients(RecipeIngredientMapper.toDTOList(recipePart.getRecipeIngredients()));
        return dto;
    }

    public static List<RecipePartDTO> toDTOList(List<RecipePart> recipeParts) {
        return recipeParts.stream().map(RecipePartMapper::toDTO).toList();
    }
}
