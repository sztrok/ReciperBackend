package com.eat.it.eatit.backend.controller;

import com.eat.it.eatit.backend.dto.CookwareDTO;
import com.eat.it.eatit.backend.dto.ItemDTO;
import com.eat.it.eatit.backend.dto.ItemInRecipeDTO;
import com.eat.it.eatit.backend.service.RecipeService;
import com.eat.it.eatit.backend.dto.RecipeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/recipe")
public class RecipeController {

    RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Recipe by ID", description = "Retrieve the details of a recipe by providing its unique ID.")
    @ApiResponse(responseCode = "200", description = "Recipe retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or non-existent recipe ID")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Long id) {
        RecipeDTO recipe = recipeService.getRecipeById(id);
        return recipe != null
                ? ResponseEntity.ok(recipe)
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("/all")
    @Operation(summary = "Get all Recipes", description = "Retrieve a list of all recipes available in the database.")
    @ApiResponse(responseCode = "200", description = "All recipes retrieved successfully")
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        List<RecipeDTO> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    @PostMapping(value = "/new", consumes = "application/json")
    @Operation(summary = "Create a new Recipe", description = "Add a new recipe to the system by providing its details in JSON format.")
    @ApiResponse(responseCode = "200", description = "Recipe added successfully")
    public ResponseEntity<RecipeDTO> addNewRecipe(@RequestBody RecipeDTO recipeDTO) {
        RecipeDTO added = recipeService.addNewRecipe(recipeDTO);
        return ResponseEntity.ok(added);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Recipe by ID", description = "Remove a recipe from the system by providing its unique ID.")
    @ApiResponse(responseCode = "200", description = "Recipe deleted successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or non-existent recipe ID")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable Long id) {
        return recipeService.deleteRecipeById(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Recipe", description = "Update the details of an existing recipe by providing its ID and updated information.")
    @ApiResponse(responseCode = "200", description = "Recipe updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or non-existent recipe ID")
    public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO) {
        RecipeDTO recipe = recipeService.updateRecipeById(id, recipeDTO);
        return recipe != null
                ? ResponseEntity.ok(recipe)
                : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{id}/info")
    @Operation(summary = "Update Recipe Information", description = "Modify general information of a recipe such as its name and description.")
    @ApiResponse(responseCode = "200", description = "Recipe information updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or non-existent recipe ID")
    public ResponseEntity<RecipeDTO> updateRecipeDescription(
            @PathVariable Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description) {
        RecipeDTO recipe = recipeService.updateDescription(id, name, description);
        return recipe != null
                ? ResponseEntity.ok(recipe)
                : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{id}/cookware")
    @Operation(summary = "Update Recipe Cookware", description = "Update the list of cookware required for preparing a specific recipe.")
    @ApiResponse(responseCode = "200", description = "Recipe cookware updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or non-existent recipe ID")
    public ResponseEntity<RecipeDTO> updateRecipeCookware(@PathVariable Long id, @RequestBody List<CookwareDTO> cookware) {
        RecipeDTO recipe = recipeService.updateCookware(id, cookware);
        return recipe != null
                ? ResponseEntity.ok(recipe)
                : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{recipeId}/items")
    @Operation(summary = "Update Items in Recipe", description = "Modify the list of ingredients included in a recipe.")
    @ApiResponse(responseCode = "200", description = "Recipe items updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or non-existent recipe ID")
    public ResponseEntity<RecipeDTO> updateRecipeItems(
            @PathVariable Long recipeId,
            @RequestBody Map<ItemDTO, Double> itemsWithAmounts
    ) {
        RecipeDTO recipe = recipeService.updateItems(recipeId, items);
        return recipe != null
                ? ResponseEntity.ok(recipe)
                : ResponseEntity.badRequest().build();
    }

    // TODO:
    //  liczenie total calories i update po zmianie itemów, dodać analogicznie dla macro? chyba tak
}
