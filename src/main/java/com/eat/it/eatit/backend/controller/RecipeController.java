package com.eat.it.eatit.backend.controller;

import com.eat.it.eatit.backend.dto.CookwareDTO;
import com.eat.it.eatit.backend.dto.ItemInRecipeDTO;
import com.eat.it.eatit.backend.service.RecipeService;
import com.eat.it.eatit.backend.dto.RecipeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a recipe by ID", description = "Fetches a recipe based on its unique identifier")
    @ApiResponse(responseCode = "200", description = "Recipe retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or non-existent recipe ID")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Long id) {
        RecipeDTO recipe = recipeService.getRecipeById(id);
        return recipe != null
                ? ResponseEntity.ok(recipe)
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("/all")
    @Operation(summary = "Retrieve all recipes", description = "Fetches all available recipes from the database")
    @ApiResponse(responseCode = "200", description = "All recipes retrieved successfully")
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        List<RecipeDTO> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    @PostMapping(value = "/new", consumes = "application/json")
    @Operation(summary = "Add a new recipe", description = "Creates a new recipe and saves it to the database")
    @ApiResponse(responseCode = "200", description = "Recipe added successfully")
    public ResponseEntity<RecipeDTO> addNewRecipe(@RequestBody RecipeDTO recipeDTO) {
        RecipeDTO added = recipeService.addNewRecipe(recipeDTO);
        return ResponseEntity.ok(added);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a recipe by ID", description = "Removes a recipe based on its unique identifier")
    @ApiResponse(responseCode = "200", description = "Recipe deleted successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or non-existent recipe ID")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable Long id) {
        return recipeService.deleteRecipeById(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update a recipe", description = "Updates an existing recipe using its ID and provided details")
    @ApiResponse(responseCode = "200", description = "Recipe updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or non-existent recipe ID")
    public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO) {
        RecipeDTO recipe = recipeService.updateRecipeById(id, recipeDTO);
        return recipe != null
                ? ResponseEntity.ok(recipe)
                : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{id}/description")
    @Operation(summary = "Update recipe description", description = "Updates the description of an existing recipe")
    @ApiResponse(responseCode = "200", description = "Recipe description updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or non-existent recipe ID")
    public ResponseEntity<RecipeDTO> updateRecipeDescription(@PathVariable Long id, @RequestBody String description) {
        RecipeDTO recipe = recipeService.updateDescription(id, description);
        return recipe != null
                ? ResponseEntity.ok(recipe)
                : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{id}/cookware")
    @Operation(summary = "Update recipe cookware", description = "Updates the cookware associated with a recipe")
    @ApiResponse(responseCode = "200", description = "Recipe cookware updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or non-existent recipe ID")
    public ResponseEntity<RecipeDTO> updateRecipeCookware(@PathVariable Long id, @RequestBody List<CookwareDTO> cookware) {
        RecipeDTO recipe = recipeService.updateCookware(id, cookware);
        return recipe != null
                ? ResponseEntity.ok(recipe)
                : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{id}/items")
    @Operation(summary = "Update recipe items", description = "Updates the items associated with a recipe")
    @ApiResponse(responseCode = "200", description = "Recipe items updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or non-existent recipe ID")
    public ResponseEntity<RecipeDTO> updateRecipeItems(@PathVariable Long id, @RequestBody List<ItemInRecipeDTO> items) {
        RecipeDTO recipe = recipeService.updateItems(id, items);
        return recipe != null
                ? ResponseEntity.ok(recipe)
                : ResponseEntity.badRequest().build();
    }

    // TODO:
    //  liczenie total calories i update po zmianie itemów, dodać analogicznie dla macro? chyba tak
}
