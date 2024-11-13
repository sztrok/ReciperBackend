package com.eat.it.eatit.backend.controller;

import com.eat.it.eatit.backend.service.RecipeService;
import com.eat.it.eatit.backend.dto.RecipeDTO;
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

    @GetMapping("/get/{id}")
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
    // TODO: delete,
    //  update wybranych pól,
    //  zmiana cookware,
    //  zmiana itemów (musze zrobic ItemInRecipe????)
}
