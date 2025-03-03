package com.eat.it.eatit.backend.service.admin;

import com.eat.it.eatit.backend.dto.recipe.RecipeDTO;
import com.eat.it.eatit.backend.mapper.refactored.recipe.RecipeRefactoredMapper;
import com.eat.it.eatit.backend.repository.recipe.RecipeRepository;
import com.eat.it.eatit.backend.service.recipe.RecipeComponentService;
import com.eat.it.eatit.backend.service.recipe.RecipeIngredientService;
import com.eat.it.eatit.backend.service.recipe.RecipeService;
import com.eat.it.eatit.backend.service.recipe.RecipeStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminRecipeService extends RecipeService {

    @Autowired
    protected AdminRecipeService(RecipeRepository repository, RecipeComponentService componentService, RecipeIngredientService ingredientService, RecipeStepService stepService) {
        super(repository, componentService, ingredientService, stepService);
    }

    public List<RecipeDTO> getAllRecipes() {
        return getAllRecipesFromDatabase().stream().map(RecipeRefactoredMapper::toDTO).toList();
    }

}
