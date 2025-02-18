package com.eat.it.eatit.backend.service.recipe;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.data.recipe.RecipeComponent;
import com.eat.it.eatit.backend.data.recipe.RecipeIngredient;
import com.eat.it.eatit.backend.data.recipe.Recipe;
import com.eat.it.eatit.backend.data.recipe.RecipeStep;
import com.eat.it.eatit.backend.dto.refactored.recipe.RecipeRefactoredDTO;
import com.eat.it.eatit.backend.enums.ItemType;
import com.eat.it.eatit.backend.repository.recipe.RecipeRepository;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public abstract class RecipeService {

    protected final RecipeRepository repository;
    protected final RecipeComponentService componentService;
    protected final RecipeIngredientService ingredientService;
    protected final RecipeStepService stepService;

    protected RecipeService(RecipeRepository repository, RecipeComponentService componentService, RecipeIngredientService ingredientService, RecipeStepService stepService) {
        this.repository = repository;
        this.componentService = componentService;
        this.ingredientService = ingredientService;
        this.stepService = stepService;
    }

    @NotNull
    protected static Recipe getRecipeRefactored(RecipeRefactoredDTO dto, List<RecipeStep> steps, List<RecipeComponent> components) {
        Recipe recipe = new Recipe();
        recipe.setName(dto.getName());
        recipe.setDescription(dto.getDescription());
        recipe.setSimpleSteps(dto.getSimpleSteps());
        recipe.setDetailedSteps(steps);
        recipe.setTips(dto.getTips());
        recipe.setImageUrl(dto.getImageUrl());
        recipe.setTags(dto.getTags());
        recipe.setRecipeComponents(components);
        recipe.setVisibility(dto.getVisibility());
        recipe.setDifficulty(dto.getDifficulty());
        return recipe;
    }

    protected Recipe findRecipeById(Long recipeId) {
        return repository.findById(recipeId).orElse(null);
    }

    protected List<Recipe> getAllRecipesFromDatabase() {
        return repository.findAll();
    }

    protected List<Item> getRecipeItems(Recipe recipe) {
        if (recipe == null) {
            return new ArrayList<>();
        }
        return recipe.getIngredients().stream().map(RecipeIngredient::getItem).toList();
    }

    protected Set<ItemType> getRecipeItemTypes(Recipe recipe) {
        if (recipe == null) {
            return new HashSet<>();
        }
        return getRecipeItems(recipe).stream().map(Item::getItemType).collect(Collectors.toSet());
    }
}
