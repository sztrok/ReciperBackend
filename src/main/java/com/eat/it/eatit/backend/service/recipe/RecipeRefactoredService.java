package com.eat.it.eatit.backend.service.recipe;

import com.eat.it.eatit.backend.repository.recipe.RecipeRefactoredRepository;

public abstract class RecipeRefactoredService {

    protected final RecipeRefactoredRepository repository;
    protected final RecipeComponentService componentService;
    protected final RecipeIngredientService ingredientService;
    protected final RecipeStepService stepService;

    protected RecipeRefactoredService(RecipeRefactoredRepository repository, RecipeComponentService componentService, RecipeIngredientService ingredientService, RecipeStepService stepService) {
        this.repository = repository;
        this.componentService = componentService;
        this.ingredientService = ingredientService;
        this.stepService = stepService;
    }
}
