package com.eat.it.eatit.backend.service.recipe;

import com.eat.it.eatit.backend.data.refactored.recipe.RecipeIngredient;
import com.eat.it.eatit.backend.data.refactored.recipe.RecipeStep;
import com.eat.it.eatit.backend.dto.refactored.recipe.RecipeStepDTO;
import com.eat.it.eatit.backend.repository.recipe.RecipeStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeStepService {

    private final RecipeStepRepository repository;
    private final RecipeIngredientService recipeIngredientService;

    @Autowired
    public RecipeStepService(RecipeStepRepository repository, RecipeIngredientService recipeIngredientService) {
        this.repository = repository;
        this.recipeIngredientService = recipeIngredientService;
    }

    public RecipeStep save(RecipeStepDTO dto) {
        RecipeStep recipeStep = new RecipeStep();
        recipeStep.setDescription(dto.getStep());
        List<RecipeIngredient> ingredients = dto.getIngredients().stream()
                .map(recipeIngredientService::save).toList();
        recipeStep.setIngredients(ingredients);
        return repository.save(recipeStep);
    }
}
