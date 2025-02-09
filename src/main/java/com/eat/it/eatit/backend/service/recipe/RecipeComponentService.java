package com.eat.it.eatit.backend.service.recipe;

import com.eat.it.eatit.backend.data.refactored.recipe.RecipeComponent;
import com.eat.it.eatit.backend.data.refactored.recipe.RecipeIngredient;
import com.eat.it.eatit.backend.dto.refactored.recipe.RecipeComponentDTO;
import com.eat.it.eatit.backend.repository.recipe.RecipeComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeComponentService {

    private final RecipeComponentRepository repository;
    private final RecipeIngredientService recipeIngredientService;

    @Autowired
    public RecipeComponentService(RecipeComponentRepository repository, RecipeIngredientService recipeIngredientService) {
        this.repository = repository;
        this.recipeIngredientService = recipeIngredientService;
    }

    public RecipeComponent save(RecipeComponentDTO dto) {
        RecipeComponent recipeComponent = new RecipeComponent();
        recipeComponent.setName(dto.getName());
        List<RecipeIngredient> ingredients = dto.getIngredients().stream()
                .map(recipeIngredientService::save).toList();
        recipeComponent.setIngredients(ingredients);
        return repository.save(recipeComponent);
    }
}
