package com.eat.it.eatit.backend.mapper.refactored.recipe;

import com.eat.it.eatit.backend.data.refactored.recipe.RecipeRefactored;
import com.eat.it.eatit.backend.dto.refactored.recipe.RecipeRefactoredDTO;

import java.util.List;

public class RecipeRefactoredMapper {

    private RecipeRefactoredMapper() {
    }

    public static RecipeRefactoredDTO toDTO(RecipeRefactored recipe) {
        RecipeRefactoredDTO dto = new RecipeRefactoredDTO();
        dto.setId(recipe.getId());
        dto.setName(recipe.getName());
        dto.setDescription(recipe.getDescription());
        dto.setSimpleSteps(recipe.getSimpleSteps());
        dto.setDetailedSteps(RecipeStepMapper.toDTOList(recipe.getDetailedSteps()));
        dto.setTips(recipe.getTips());
        dto.setImageUrl(recipe.getImageUrl());
        dto.setTags(recipe.getTags());
        dto.setRecipeComponents(RecipeComponentMapper.toDTOList(recipe.getRecipeComponents()));
        dto.setVisibility(recipe.getVisibility());
        dto.setDifficulty(recipe.getDifficulty());
        return dto;
    }

    public static List<RecipeRefactoredDTO> toDTOList(List<RecipeRefactored> recipes) {
        return recipes.stream().map(RecipeRefactoredMapper::toDTO).toList();
    }
}

