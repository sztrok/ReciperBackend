package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.Recipe;
import com.eat.it.eatit.backend.dto.RecipeDTO;
import com.eat.it.eatit.backend.dto.recipe.RecipeDetailsDTO;
import com.eat.it.eatit.backend.dto.recipe.RecipeSimpleDTO;
import com.eat.it.eatit.backend.mapper.simple.ItemWithAmountMapper;

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
                recipe.getDetailedSteps(),
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

    public static RecipeDetailsDTO toDetailsDTO(Recipe recipe) {
        RecipeDetailsDTO recipeDetailsDTO = new RecipeDetailsDTO();
        recipeDetailsDTO.setId(recipe.getId());
        recipeDetailsDTO.setName(recipe.getName());
        recipeDetailsDTO.setDescription(recipe.getDescription());
        recipeDetailsDTO.setSimpleSteps(recipe.getSimpleSteps());
        recipeDetailsDTO.setComplexSteps(recipe.getDetailedSteps());
        recipeDetailsDTO.setItems(ItemWithAmountMapper.toDTOFromRecipe(recipe.getItems()));
        recipeDetailsDTO.setCookware(CookwareMapper.toDTOList(recipe.getCookware()));
        recipeDetailsDTO.setVisibility(recipe.getVisibility().getDescription());
        recipeDetailsDTO.setDifficulty(recipe.getDifficulty().getDescription());
        return recipeDetailsDTO;
    }

    public static RecipeSimpleDTO toSimpleDTO(Recipe recipe) {
        RecipeSimpleDTO recipeSimpleDTO = new RecipeSimpleDTO();
        recipeSimpleDTO.setId(recipe.getId());
        recipeSimpleDTO.setName(recipe.getName());
        recipeSimpleDTO.setDifficulty(recipe.getDifficulty().getDescription());
        recipeSimpleDTO.setVisibility(recipe.getVisibility().getDescription());
        return recipeSimpleDTO;
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

    public static List<RecipeDetailsDTO> toDetailsDTOList(List<Recipe> recipes) {
        return recipes.stream().map(RecipeMapper::toDetailsDTO).toList();
    }

    public static List<RecipeSimpleDTO> toSimpleDTOList(List<Recipe> recipes) {
        return recipes.stream().map(RecipeMapper::toSimpleDTO).toList();
    }
}
