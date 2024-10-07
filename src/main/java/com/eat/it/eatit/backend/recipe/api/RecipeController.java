package com.eat.it.eatit.backend.recipe.api;

import com.eat.it.eatit.backend.recipe.data.RecipeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipe")
public class RecipeController {

    RecipeHandler recipeHandler;

    @Autowired
    public RecipeController(RecipeHandler recipeHandler) {
        this.recipeHandler = recipeHandler;
    }

    @GetMapping("/test")
    public String test() {
        return "Success";
    }

    @GetMapping("/get/id/{id}")
    public RecipeDTO getRecipeById(@PathVariable Long id) {
        return recipeHandler.getRecipeById(id);
    }

    @GetMapping("/get_all")
    public List<RecipeDTO> getAllRecipes() {
        return recipeHandler.getAllRecipes();
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<RecipeDTO> addNewRecipe(@RequestBody RecipeDTO recipeDTO) {
        return recipeHandler.addNewRecipe(recipeDTO);
    }
}
