package com.eat.it.eatit.backend.mappers;

import com.eat.it.eatit.backend.data.Recipe;
import com.eat.it.eatit.backend.dto.RecipeDTO;

import java.util.HashSet;
import java.util.Set;

public class RecipeMapper {

    //TODO: add null checks
    private RecipeMapper() {
    }

    public static RecipeDTO toDTO(Recipe recipe) {
        return new RecipeDTO(
                recipe.getName(),
                recipe.getOwnerId(),
                recipe.getDescription(),
                ItemMapper.toDTOSet(recipe.getItems()),
                CookwareMapper.toDTOSet(recipe.getCookware()),
                recipe.getTotalCalories());
    }

    public static Recipe toEntity(RecipeDTO recipeDTO) {
        return new Recipe(
                recipeDTO.getName(),
                recipeDTO.getOwnerId(),
                recipeDTO.getDescription(),
                ItemMapper.toEntitySet(recipeDTO.getItems()),
                CookwareMapper.toEntitySet(recipeDTO.getCookware()),
                recipeDTO.getTotalCalories());
    }

    public static Set<RecipeDTO> toDTOSet(Set<Recipe> recipes) {
        Set<RecipeDTO> recipeDTOSet = new HashSet<>();
        for(Recipe recipe: recipes) {
            recipeDTOSet.add(
                    new RecipeDTO(
                            recipe.getName(),
                            recipe.getOwnerId(),
                            recipe.getDescription(),
                            ItemMapper.toDTOSet(recipe.getItems()),
                            CookwareMapper.toDTOSet(recipe.getCookware()),
                            recipe.getTotalCalories()));
        }
        return recipeDTOSet;
    }

    public static Set<Recipe> toEntitySet(Set<RecipeDTO> recipes) {
        Set<Recipe> recipeEntitySet = new HashSet<>();
        for(RecipeDTO recipe: recipes) {
            recipeEntitySet.add(
                    new Recipe(
                            recipe.getName(),
                            recipe.getOwnerId(),
                            recipe.getDescription(),
                            ItemMapper.toEntitySet(recipe.getItems()),
                            CookwareMapper.toEntitySet(recipe.getCookware()),
                            recipe.getTotalCalories()));
        }
        return recipeEntitySet;
    }
}
