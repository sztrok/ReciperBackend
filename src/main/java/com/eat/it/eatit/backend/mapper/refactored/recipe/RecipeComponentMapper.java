package com.eat.it.eatit.backend.mapper.refactored.recipe;

import com.eat.it.eatit.backend.data.recipe.RecipeComponent;
import com.eat.it.eatit.backend.dto.recipe.RecipeComponentDTO;

import java.util.List;

public class RecipeComponentMapper {

    private RecipeComponentMapper() {
    }

    public static RecipeComponentDTO toDTO(RecipeComponent recipeComponent) {
        RecipeComponentDTO dto = new RecipeComponentDTO();
        dto.setId(recipeComponent.getId());
        dto.setName(recipeComponent.getName());
        dto.setIngredients(RecipeIngredientMapper.toDTOList(recipeComponent.getIngredients()));
        return dto;
    }

    public static List<RecipeComponentDTO> toDTOList(List<RecipeComponent> recipeComponents) {
        return recipeComponents.stream().map(RecipeComponentMapper::toDTO).toList();
    }
}
