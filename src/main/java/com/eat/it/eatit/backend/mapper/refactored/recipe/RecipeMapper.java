package com.eat.it.eatit.backend.mapper.refactored.recipe;

import com.eat.it.eatit.backend.data.refactored.recipe.RecipeRefactored;
import com.eat.it.eatit.backend.dto.refactored.recipe.RecipeDTO;

public class RecipeMapper {

    private RecipeMapper() {
    }

    public static RecipeDTO toDTO(RecipeRefactored recipe) {
        RecipeDTO dto = new RecipeDTO();
        dto.setId(recipe.getId());
        dto.setDescription(recipe.getDescription());
        dto.setSimpleSteps(recipe.getSimpleSteps());
        dto.setDetailedSteps(RecipeStepMapper.toDTOList(recipe.getDetailedSteps()));
        dto.setTips(recipe.getTips());
        dto.setImageUrl(recipe.getImageUrl());
        dto.setTags(recipe.getTags());
        dto.setRecipeParts(RecipePartMapper.toDTOList(recipe.getRecipeParts()));
        return dto;
    }
}

