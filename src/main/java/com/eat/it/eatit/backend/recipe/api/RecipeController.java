package com.eat.it.eatit.backend.recipe.api;

import com.eat.it.eatit.backend.recipe.data.RecipeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipe")
public class RecipeController {

    RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/test")
    public String test() {
        return "Success";
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id);
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<RecipeDTO> addNewRecipe(@RequestBody RecipeDTO recipeDTO) {
        return recipeService.addNewRecipe(recipeDTO);
    }
}
