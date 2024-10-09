package com.eat.it.eatit.backend.recipe.data;

import com.eat.it.eatit.backend.cookware.data.CookwareMapper;
import com.eat.it.eatit.backend.item.data.ItemMapper;

import java.util.HashSet;
import java.util.Set;

public class RecipeMapper {

    private RecipeMapper() {
    }

    public static RecipeDTO toDTO(Recipe recipe) {
        if(recipe == null) {
            return new RecipeDTO();
        }
        return new RecipeDTO(
                recipe.getName(),
                recipe.getDescription(),
                ItemMapper.toDTOSet(recipe.getItems()),
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
                ItemMapper.toEntitySet(recipeDTO.getItems()),
                CookwareMapper.toEntitySet(recipeDTO.getCookware()),
                recipeDTO.getTotalCalories());
    }

    public static Set<RecipeDTO> toDTOSet(Set<Recipe> recipeSet) {
        if(recipeSet == null) {
            return new HashSet<>();
        }
        Set<RecipeDTO> recipeDTOSet = new HashSet<>();
        for(Recipe recipe: recipeSet) {
            recipeDTOSet.add(
                    new RecipeDTO(
                            recipe.getName(),
                            recipe.getDescription(),
                            ItemMapper.toDTOSet(recipe.getItems()),
                            CookwareMapper.toDTOSet(recipe.getCookware()),
                            recipe.getTotalCalories()));
        }
        return recipeDTOSet;
    }

    public static Set<Recipe> toEntitySet(Set<RecipeDTO> recipeDTOSet) {
        if(recipeDTOSet == null) {
            return new HashSet<>();
        }
        Set<Recipe> recipeEntitySet = new HashSet<>();
        for(RecipeDTO recipe: recipeDTOSet) {
            recipeEntitySet.add(
                    new Recipe(
                            recipe.getName(),
                            recipe.getDescription(),
                            ItemMapper.toEntitySet(recipe.getItems()),
                            CookwareMapper.toEntitySet(recipe.getCookware()),
                            recipe.getTotalCalories()));
        }
        return recipeEntitySet;
    }
}
