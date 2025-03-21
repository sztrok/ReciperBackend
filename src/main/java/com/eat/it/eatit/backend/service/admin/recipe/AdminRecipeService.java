package com.eat.it.eatit.backend.service.admin.recipe;

import com.eat.it.eatit.backend.dto.recipe.RecipeDTO;
import com.eat.it.eatit.backend.mapper.refactored.recipe.RecipeRefactoredMapper;
import com.eat.it.eatit.backend.repository.recipe.RecipeRepository;
import com.eat.it.eatit.backend.service.general.recipe.RecipeComponentService;
import com.eat.it.eatit.backend.service.general.recipe.RecipeIngredientService;
import com.eat.it.eatit.backend.service.general.recipe.RecipeService;
import com.eat.it.eatit.backend.service.general.recipe.RecipeStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminRecipeService extends RecipeService {

    @Autowired
    protected AdminRecipeService(RecipeRepository repository, RecipeComponentService componentService, RecipeIngredientService ingredientService, RecipeStepService stepService) {
        super(repository, componentService, ingredientService, stepService);
    }

    public List<RecipeDTO> getAllRecipes(Optional<Long> limit) {
        List<RecipeDTO> allRecipes = getAllRecipesFromDatabase().stream().map(RecipeRefactoredMapper::toDTO).toList();
        return limit.map(aLong -> allRecipes.stream().limit(aLong).toList()).orElse(allRecipes);
    }

}
