package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.Recipe;
import com.eat.it.eatit.backend.dto.RecipeDTO;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RecipeMapper {

    private RecipeMapper() {
    }

    public static RecipeDTO toDTO(Recipe recipe) {
        if(recipe == null) {
            return new RecipeDTO();
        }
        return new RecipeDTO(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                ItemInRecipeMapper.toDTOSet(recipe.getItems()),
                CookwareMapper.toDTOSet(recipe.getCookware()),
                recipe.getTotalCalories());
    }

    public static Recipe toEntity(RecipeDTO recipeDTO) {
        if(recipeDTO == null) {
            return new Recipe();
        }
        return new Recipe(
                recipeDTO.getName(),
                recipeDTO.getDescription(),
                ItemInRecipeMapper.toEntitySet(recipeDTO.getItems()),
                CookwareMapper.toEntitySet(recipeDTO.getCookware()),
                recipeDTO.getTotalCalories());
    }

    public static Set<RecipeDTO> toDTOSet(Set<Recipe> recipeSet) {
        if(recipeSet == null) {
            return new HashSet<>();
        }
        return recipeSet.stream().map(RecipeMapper::toDTO).collect(Collectors.toSet());
    }

    public static Set<Recipe> toEntitySet(Set<RecipeDTO> recipeDTOSet) {
        if(recipeDTOSet == null) {
            return new HashSet<>();
        }
        return recipeDTOSet.stream().map(RecipeMapper::toEntity).collect(Collectors.toSet());
    }
}
