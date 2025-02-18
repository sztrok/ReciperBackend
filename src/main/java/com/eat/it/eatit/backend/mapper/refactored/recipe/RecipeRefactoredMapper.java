package com.eat.it.eatit.backend.mapper.refactored.recipe;

import com.eat.it.eatit.backend.data.recipe.Recipe;
import com.eat.it.eatit.backend.dto.recipe.RecipeDTO;

import java.util.List;

public class RecipeRefactoredMapper {

    private RecipeRefactoredMapper() {
    }

    public static RecipeDTO toDTO(Recipe recipe) {
        RecipeDTO dto = new RecipeDTO();
        dto.setId(recipe.getId());
        dto.setName(recipe.getName());
        dto.setDescription(recipe.getDescription());
        dto.setSimpleSteps(recipe.getSimpleSteps());
        dto.setDetailedSteps(RecipeStepMapper.toDTOList(recipe.getDetailedSteps()));
        dto.setTips(recipe.getTips());
        dto.setImageUrl(recipe.getImageUrl());
        dto.setTags(recipe.getTags());
        dto.setRecipeComponents(RecipeComponentMapper.toDTOList(recipe.getRecipeComponents()));
        dto.setIngredients(RecipeIngredientMapper.toDTOList(recipe.getIngredients()));
        dto.setVisibility(recipe.getVisibility());
        dto.setDifficulty(recipe.getDifficulty());
        dto.setNumberOfLikedAccounts(recipe.getLikedAccounts().size());
        dto.setNumberOfIngredients(recipe.getIngredients().size());
        return dto;
    }

    public static List<RecipeDTO> toDTOList(List<Recipe> recipes) {
        return recipes.stream().map(RecipeRefactoredMapper::toDTO).toList();
    }
}

