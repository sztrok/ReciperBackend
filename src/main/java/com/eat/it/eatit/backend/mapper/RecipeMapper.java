package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.Recipe;
import com.eat.it.eatit.backend.dto.RecipeDTO;

import java.util.ArrayList;
import java.util.List;

public class RecipeMapper {

    private RecipeMapper() {
    }

    public static RecipeDTO toDTO(Recipe recipe) {
        if (recipe == null) {
            return new RecipeDTO();
        }
        return new RecipeDTO(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getSimpleSteps(),
                recipe.getComplexSteps(),
                ItemInRecipeMapper.toDTOList(recipe.getItems()),
                CookwareMapper.toDTOList(recipe.getCookware()),
                recipe.getTotalCalories(),
                recipe.getVisibility(),
                recipe.getDifficulty());
    }

    public static Recipe toEntity(RecipeDTO recipeDTO) {
        if (recipeDTO == null) {
            return new Recipe();
        }
        return new Recipe(
                recipeDTO.getName(),
                recipeDTO.getDescription(),
                recipeDTO.getSimpleSteps(),
                recipeDTO.getComplexSteps(),
                ItemInRecipeMapper.toEntityList(recipeDTO.getItems()),
                CookwareMapper.toEntityList(recipeDTO.getCookware()),
                recipeDTO.getTotalCalories(),
                recipeDTO.getVisibility(),
                recipeDTO.getDifficulty());
    }

    public static List<RecipeDTO> toDTOList(List<Recipe> recipeSet) {
        if (recipeSet == null) {
            return new ArrayList<>();
        }
        return recipeSet.stream().map(RecipeMapper::toDTO).toList();
    }

    public static List<Recipe> toEntityList(List<RecipeDTO> recipeDTOSet) {
        if (recipeDTOSet == null) {
            return new ArrayList<>();
        }
        return recipeDTOSet.stream().map(RecipeMapper::toEntity).toList();
    }
}
