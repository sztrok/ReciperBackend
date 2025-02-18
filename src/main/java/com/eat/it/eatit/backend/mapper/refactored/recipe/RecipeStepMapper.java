package com.eat.it.eatit.backend.mapper.refactored.recipe;

import com.eat.it.eatit.backend.data.recipe.RecipeStep;
import com.eat.it.eatit.backend.dto.refactored.recipe.RecipeStepDTO;

import java.util.List;

public class RecipeStepMapper {

    private RecipeStepMapper() {
    }

    public static RecipeStepDTO toDTO(RecipeStep recipeStep) {
        RecipeStepDTO dto = new RecipeStepDTO();
        dto.setId(recipeStep.getId());
        dto.setStep(recipeStep.getDescription());
        dto.setIngredients(RecipeIngredientMapper.toDTOList(recipeStep.getIngredients()));
        return dto;
    }

    public static List<RecipeStepDTO> toDTOList(List<RecipeStep> recipeSteps) {
        return recipeSteps.stream().map(RecipeStepMapper::toDTO).toList();
    }
}
