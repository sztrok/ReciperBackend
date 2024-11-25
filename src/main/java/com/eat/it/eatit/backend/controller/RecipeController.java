package com.eat.it.eatit.backend.controller;

import com.eat.it.eatit.backend.dto.CookwareDTO;
import com.eat.it.eatit.backend.dto.ItemInRecipeDTO;
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

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Long id) {
        RecipeDTO recipe = recipeService.getRecipeById(id);
        return recipe != null
                ? ResponseEntity.ok(recipe)
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        List<RecipeDTO> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    @PostMapping(value = "/new", consumes = "application/json")
    public ResponseEntity<RecipeDTO> addNewRecipe(@RequestBody RecipeDTO recipeDTO) {
        RecipeDTO added = recipeService.addNewRecipe(recipeDTO);
        return ResponseEntity.ok(added);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable Long id) {
        return recipeService.deleteRecipeById(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO) {
        RecipeDTO recipe = recipeService.updateRecipeById(id, recipeDTO);
        return recipe != null
                ? ResponseEntity.ok(recipe)
                : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{id}/description")
    public ResponseEntity<RecipeDTO> updateRecipeDescription(@PathVariable Long id, @RequestBody String description) {
        RecipeDTO recipe = recipeService.updateDescription(id, description);
        return recipe != null
                ? ResponseEntity.ok(recipe)
                : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{id}/cookware")
    public ResponseEntity<RecipeDTO> updateRecipeCookware(@PathVariable Long id, @RequestBody List<CookwareDTO> cookware) {
        RecipeDTO recipe = recipeService.updateCookware(id, cookware);
        return recipe != null
                ? ResponseEntity.ok(recipe)
                : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{id}/items")
    public ResponseEntity<RecipeDTO> updateRecipeItems(@PathVariable Long id, @RequestBody List<ItemInRecipeDTO> items) {
        RecipeDTO recipe = recipeService.updateItems(id, items);
        return recipe != null
                ? ResponseEntity.ok(recipe)
                : ResponseEntity.badRequest().build();
    }

    // TODO:
    //  liczenie total calories i update po zmianie itemów, dodać analogicznie dla macro? chyba tak
}
