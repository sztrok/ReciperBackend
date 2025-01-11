package com.eat.it.eatit.backend.controller.user;

import com.eat.it.eatit.backend.dto.RecipeDTO;
import com.eat.it.eatit.backend.enums.ItemType;
import com.eat.it.eatit.backend.enums.RecipeDifficulty;
import com.eat.it.eatit.backend.service.user.UserRecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('ROLE_USER')")
@RequestMapping("/api/v1/user/recipe")
public class UserRecipeController {

    UserRecipeService recipeService;

    @Autowired
    public UserRecipeController(UserRecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Recipe by ID", description = "Retrieve the details of a recipe by providing its unique ID.")
    @ApiResponse(responseCode = "200", description = "Recipe retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or non-existent recipe ID")
    public ResponseEntity<RecipeDTO> getPublicRecipeById(@PathVariable Long id) {
        RecipeDTO recipe = recipeService.getPublicRecipeById(id);
        return recipe != null
                ? ResponseEntity.ok(recipe)
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("/all")
    @Operation(summary = "Get all public Recipes", description = "Retrieve a list of all public recipes available in the database.")
    @ApiResponse(responseCode = "200", description = "All public recipes retrieved successfully")
    public ResponseEntity<List<RecipeDTO>> getAllPublicRecipes() {
        List<RecipeDTO> recipes = recipeService.getAllPublicRecipes();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/items/item_types")
    @Operation(summary = "Get Recipes by Item Types", description = "Retrieve a list of recipes that use specific item types.")
    @ApiResponse(responseCode = "200", description = "Recipes retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or non-existent item types")
    public ResponseEntity<List<RecipeDTO>> getRecipeByItemTypes(@RequestParam List<ItemType> itemTypes) {
        List<RecipeDTO> recipes = recipeService.getPublicRecipesByItemTypes(itemTypes);
        return recipes != null && !recipes.isEmpty()
                ? ResponseEntity.ok(recipes)
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("/difficulty")
    @Operation(summary = "Get Recipes by Difficulty", description = "Retrieve recipes based on selected difficulty levels such as 'EASY', 'MEDIUM', or 'HARD'.")
    @ApiResponse(responseCode = "200", description = "Recipes retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or unsupported difficulty levels")
    public ResponseEntity<List<RecipeDTO>> getRecipesByDifficulty(@RequestParam List<RecipeDifficulty> difficultyList) {
        List<RecipeDTO> recipes = recipeService.getPublicRecipesByDifficulty(difficultyList);
        return recipes != null && !recipes.isEmpty()
                ? ResponseEntity.ok(recipes)
                : ResponseEntity.badRequest().build();
    }

}
