package com.eat.it.eatit.backend.mapper.simple;

import com.eat.it.eatit.backend.data.Recipe;
import com.eat.it.eatit.backend.dto.simple.RecipeSimpleDTO;
import com.eat.it.eatit.backend.mapper.CookwareMapper;

import java.util.List;

public class RecipeSimpleMapper {

    private RecipeSimpleMapper() {
    }

    public static RecipeSimpleDTO toSimpleDTO(Recipe recipe) {
        RecipeSimpleDTO recipeSimpleDTO = new RecipeSimpleDTO();
        recipeSimpleDTO.setId(recipe.getId());
        recipeSimpleDTO.setName(recipe.getName());
        recipeSimpleDTO.setDescription(recipe.getDescription());
        recipeSimpleDTO.setSimpleSteps(recipe.getSimpleSteps());
        recipeSimpleDTO.setComplexSteps(recipe.getComplexSteps());
        recipeSimpleDTO.setItems(ItemWithAmountMapper.toDTOFromRecipe(recipe.getItems()));
        recipeSimpleDTO.setCookware(CookwareMapper.toDTOList(recipe.getCookware()));
        recipeSimpleDTO.setVisibility(recipe.getVisibility().getDescription());
        recipeSimpleDTO.setDifficulty(recipe.getDifficulty().getDescription());
        return recipeSimpleDTO;
    }

    public static List<RecipeSimpleDTO> toSimpleDTOList(List<Recipe> recipes) {
        return recipes.stream().map(RecipeSimpleMapper::toSimpleDTO).toList();
    }

}
