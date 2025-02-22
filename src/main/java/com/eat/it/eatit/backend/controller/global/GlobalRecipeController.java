package com.eat.it.eatit.backend.controller.global;

import com.eat.it.eatit.backend.dto.recipe.RecipeDTO;
import com.eat.it.eatit.backend.dto.recipe.fastapi.RecipeFastApiRequest;
import com.eat.it.eatit.backend.enums.ItemType;
import com.eat.it.eatit.backend.enums.RecipeDifficulty;
import com.eat.it.eatit.backend.enums.SortingParameter;
import com.eat.it.eatit.backend.service.global.GlobalRecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
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
    public ResponseEntity<RecipeDTO> getPublicRecipeById(Authentication authentication, @PathVariable Long id) {
        RecipeDTO recipe = recipeService.getPublicRecipeById(authentication, id);
        return recipe != null
                ? ResponseEntity.ok(recipe)
                : ResponseEntity.badRequest().build();
    }

    @GetMapping
    @Operation(summary = "Get public Recipes", description = "Retrieve all public recipes optionally containing specified ingredients.")
    @ApiResponse(responseCode = "200", description = "Recipes retrieved successfully")
    public ResponseEntity<List<RecipeDTO>> getPublicRecipes(
            Authentication authentication,
            @RequestParam(required = false) List<String> ingredients,
            @RequestParam(defaultValue = "NUM_OF_LIKES") SortingParameter sortingParameter
            ) {
        return ResponseEntity.ok(recipeService.getRecipes(
                authentication,
                ingredients == null || ingredients.isEmpty() ? Optional.empty() : Optional.of(ingredients),
                sortingParameter
        ));
    }

    @GetMapping("/items/item_types")
    @Operation(summary = "Get Recipes by Item Types", description = "Retrieve a list of recipes that use specific item types.")
    @ApiResponse(responseCode = "200", description = "Recipes retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or non-existent item types")
    public ResponseEntity<List<RecipeDTO>> getRecipeByItemTypes(
            Authentication authentication,
            @RequestParam List<ItemType> itemTypes
    ) {
        List<RecipeDTO> recipes = recipeService.getPublicRecipesByItemTypes(authentication, itemTypes);
        return recipes != null && !recipes.isEmpty()
                ? ResponseEntity.ok(recipes)
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("/difficulty")
    @Operation(summary = "Get Recipes by Difficulty", description = "Retrieve recipes based on selected difficulty levels such as 'EASY', 'MEDIUM', or 'HARD'.")
    @ApiResponse(responseCode = "200", description = "Recipes retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or unsupported difficulty levels")
    public ResponseEntity<List<RecipeDTO>> getRecipesByDifficulty(
            Authentication authentication,
            @RequestParam List<RecipeDifficulty> difficultyList
    ) {
        List<RecipeDTO> recipes = recipeService.getPublicRecipesByDifficulty(authentication, difficultyList);
        return recipes != null && !recipes.isEmpty()
                ? ResponseEntity.ok(recipes)
                : ResponseEntity.badRequest().build();
    }

    @PostMapping
    @Operation(summary = "Creates new recipe", description = "Creates new recipe from template and saves it in database")
    @ApiResponse(responseCode = "200", description = "Recipes created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or unsupported difficulty levels")
    public ResponseEntity<RecipeDTO> addNewRecipe(
            Authentication authentication,
            @RequestBody RecipeDTO recipe
    ) {
        RecipeDTO newRecipe = recipeService.addNewRecipe(recipe);
        return ResponseEntity.ok(newRecipe);
    }

    @PostMapping("/generator")
    @Operation(summary = "Generate recipe from description", description = "Generates recipe matching template from description containing all necessary information")
    @ApiResponse(responseCode = "200", description = "Recipes generated successfully")
    public ResponseEntity<RecipeDTO> generateRecipeFromDescription(@RequestBody RecipeFastApiRequest recipe) {
        log.info("Generate recipe from description");
        RecipeDTO body = recipeService.generateNewRecipeWithFastApiConnection(recipe);
        log.info("Recipe from description generated successfully");
        return ResponseEntity.ok(body);
    }

    @PostMapping("/prompt")
    @Operation(summary = "Generate recipe from prompt", description = "Generates recipe matching template from simple prompt")
    @ApiResponse(responseCode = "200", description = "Recipes generated successfully")
    public ResponseEntity<RecipeDTO> generateRecipeFromPrompt(@RequestBody RecipeFastApiRequest recipe) {
        log.info("Generate recipe from prompt");
        RecipeDTO body = recipeService.generateRecipeFromPrompt(recipe);
        log.info("Recipe from prompt generated successfully");
        return ResponseEntity.ok(body);
    }

}
