package com.eat.it.eatit.backend.recipe.api;

import com.eat.it.eatit.backend.recipe.data.Recipe;
import com.eat.it.eatit.backend.recipe.data.RecipeDTO;
import com.eat.it.eatit.backend.recipe.data.RecipeMapper;
import com.eat.it.eatit.backend.recipe.data.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeHandler {

    RecipeRepository recipeRepository;

    @Autowired
    public RecipeHandler(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public RecipeDTO getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        return RecipeMapper.toDTO(recipe);
    }

    public List<RecipeDTO> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        List<RecipeDTO> recipeDTOList = new ArrayList<>();
        for (Recipe recipe : recipes) {
            recipeDTOList.add(RecipeMapper.toDTO(recipe));
        }
        return recipeDTOList;
    }

    public ResponseEntity<RecipeDTO> addNewRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = RecipeMapper.toEntity(recipeDTO);
        recipe = recipeRepository.save(recipe);
        return ResponseEntity.ok(RecipeMapper.toDTO(recipe));
    }
}
