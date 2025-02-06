package com.eat.it.eatit.backend.controller.global;

import com.eat.it.eatit.backend.dto.refactored.recipe.RecipeRefactoredDTO;
import com.eat.it.eatit.backend.enums.ItemType;
import com.eat.it.eatit.backend.enums.RecipeDifficulty;
import com.eat.it.eatit.backend.service.global.GlobalRecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/global/recipe")
public class GlobalRecipeController {

    GlobalRecipeService recipeService;

    @Autowired
    public GlobalRecipeController(GlobalRecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Recipe by ID", description = "Retrieve the details of a public recipe by providing its unique ID.")
    @ApiResponse(responseCode = "200", description = "Recipe retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or non-existent recipe ID")
    public ResponseEntity<RecipeRefactoredDTO> getPublicRecipeById(@PathVariable Long id) {
        RecipeRefactoredDTO recipe = recipeService.getPublicRecipeById(id);
        return recipe != null
                ? ResponseEntity.ok(recipe)
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("/all")
    @Operation(summary = "Get all public Recipes", description = "Retrieve a list of all public recipes available in the database.")
    @ApiResponse(responseCode = "200", description = "All public recipes retrieved successfully")
    public ResponseEntity<List<RecipeRefactoredDTO>> getAllPublicRecipes() {
        List<RecipeRefactoredDTO> recipes = recipeService.getAllPublicRecipes();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/items/item_types")
    @Operation(summary = "Get Recipes by Item Types", description = "Retrieve a list of recipes that use specific item types.")
    @ApiResponse(responseCode = "200", description = "Recipes retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or non-existent item types")
    public ResponseEntity<List<RecipeRefactoredDTO>> getRecipeByItemTypes(@RequestParam List<ItemType> itemTypes) {
        List<RecipeRefactoredDTO> recipes = recipeService.getPublicRecipesByItemTypes(itemTypes);
        return recipes != null && !recipes.isEmpty()
                ? ResponseEntity.ok(recipes)
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("/difficulty")
    @Operation(summary = "Get Recipes by Difficulty", description = "Retrieve recipes based on selected difficulty levels such as 'EASY', 'MEDIUM', or 'HARD'.")
    @ApiResponse(responseCode = "200", description = "Recipes retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or unsupported difficulty levels")
    public ResponseEntity<List<RecipeRefactoredDTO>> getRecipesByDifficulty(@RequestParam List<RecipeDifficulty> difficultyList) {
        List<RecipeRefactoredDTO> recipes = recipeService.getPublicRecipesByDifficulty(difficultyList);
        return recipes != null && !recipes.isEmpty()
                ? ResponseEntity.ok(recipes)
                : ResponseEntity.badRequest().build();
    }

}
