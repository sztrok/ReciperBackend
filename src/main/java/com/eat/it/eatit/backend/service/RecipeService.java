package com.eat.it.eatit.backend.service;

import com.eat.it.eatit.backend.data.Recipe;
import com.eat.it.eatit.backend.dto.RecipeDTO;
import com.eat.it.eatit.backend.mapper.RecipeMapper;
import com.eat.it.eatit.backend.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {

    RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public ResponseEntity<RecipeDTO> getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(RecipeMapper.toDTO(recipe));
    }

    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        List<RecipeDTO> recipeDTOList = new ArrayList<>();
        for (Recipe recipe : recipes) {
            recipeDTOList.add(RecipeMapper.toDTO(recipe));
        }
        return ResponseEntity.ok(recipeDTOList);
    }

    public ResponseEntity<RecipeDTO> addNewRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = RecipeMapper.toEntity(recipeDTO);
        recipe = recipeRepository.save(recipe);
        return ResponseEntity.ok(RecipeMapper.toDTO(recipe));
    }
}
