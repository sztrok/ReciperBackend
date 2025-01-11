package com.eat.it.eatit.backend.utils.recipe;

import com.eat.it.eatit.backend.data.Recipe;
import com.eat.it.eatit.backend.dto.RecipeDTO;
import com.eat.it.eatit.backend.mapper.CookwareMapper;
import com.eat.it.eatit.backend.mapper.ItemInRecipeMapper;

import static com.eat.it.eatit.backend.mapper.RecipeMapper.toDTO;
import static com.eat.it.eatit.backend.utils.UtilsKt.updateField;

public class UpdateRecipeFields {

    private UpdateRecipeFields() {}

    public static RecipeDTO updateRecipeFields(RecipeDTO recipeDTO, Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        updateField(recipeDTO.getName(), recipe::setName);
        updateField(recipeDTO.getDescription(), recipe::setDescription);
        updateField(recipeDTO.getSimpleSteps(), recipe::setSimpleSteps);
        updateField(recipeDTO.getComplexSteps(), recipe::setComplexSteps);
        updateField(recipeDTO.getItems(), r -> recipe.setItems(ItemInRecipeMapper.toEntityList(r)));
        updateField(recipeDTO.getCookware(), r -> recipe.setCookware(CookwareMapper.toEntityList(r)));
        updateField(recipeDTO.getVisibility(), recipe::setVisibility);
        updateField(recipeDTO.getDifficulty(), recipe::setDifficulty);
        return toDTO(recipe);
    }
}
