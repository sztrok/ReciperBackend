package com.eat.it.eatit.backend.controller.user.account;

import com.eat.it.eatit.backend.dto.refactored.recipe.RecipeRefactoredDTO;
import com.eat.it.eatit.backend.service.user.account.UserAccountRecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@PreAuthorize("hasAuthority('ROLE_USER')")
@RequestMapping("/api/v1/user/account/recipe")
public class UserAccountRecipeController {

    UserAccountRecipeService recipeService;

    @Autowired
    public UserAccountRecipeController(UserAccountRecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("account_recipes")
    @Operation(summary = "Get account recipes", description = "Returns list of recipes assigned to this account, or empty list if account has no assigned recipes")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved recipes assigned to Account")
    public ResponseEntity<List<RecipeRefactoredDTO>> getAccountRecipes(Authentication authentication) {
        log.info("Get account recipes");
        return ResponseEntity.ok(recipeService.getAccountRecipes(authentication));
    }

    @GetMapping("account_recipes/{recipeId}")
    @Operation(summary = "Get account recipe by id")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved recipe details")
    public ResponseEntity<RecipeRefactoredDTO> getAccountRecipe(Authentication authentication, @PathVariable Long recipeId) {
        RecipeRefactoredDTO result = recipeService.getAccountRecipeById(authentication, recipeId);
        return result != null
                ? ResponseEntity.ok(result)
                : ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "account_recipes", consumes = "application/json")
    @Operation(summary = "Create a new Recipe", description = "Add a new recipe to assigned to account.")
    @ApiResponse(responseCode = "200", description = "Recipe added successfully")
    public ResponseEntity<RecipeRefactoredDTO> addNewRecipe(Authentication authentication, @RequestBody RecipeRefactoredDTO recipeDTO) {
        RecipeRefactoredDTO added = recipeService.addNewAccountRecipe(authentication, recipeDTO);
        return ResponseEntity.ok(added);
    }

    @PutMapping("account_recipes/{recipeId}")
    @Operation(summary = "Update recipe", description = "Updates account's exisitng recipe.")
    @ApiResponse(responseCode = "200", description = "Successfully added liked recipes.")
    @ApiResponse(responseCode = "400", description = "Account not found.")
    public ResponseEntity<RecipeRefactoredDTO> updateAccountRecipe(Authentication authentication, @PathVariable Long recipeId) {
        RecipeRefactoredDTO updatedRecipe = recipeService.updateAccountRecipe(authentication, recipeId);
        return ResponseEntity.ok(updatedRecipe);
    }

    @GetMapping("liked_recipes")
    @Operation(summary = "Get liked recipes", description = "Returns list of recipes liked by this account, or empty list if account has no liked recipes")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved recipes liked by this Account")
    public ResponseEntity<List<RecipeRefactoredDTO>> getLikedRecipes(Authentication authentication) {
        return ResponseEntity.ok(recipeService.getLikedRecipes(authentication));
    }

    @PutMapping("liked_recipes")
    @Operation(summary = "Add liked recipes", description = "Adds a list of liked recipes to an existing account.")
    @ApiResponse(responseCode = "200", description = "Successfully added liked recipes.")
    @ApiResponse(responseCode = "400", description = "Account not found.")
    public ResponseEntity<List<RecipeRefactoredDTO>> addLikedRecipes(Authentication authentication, @RequestBody List<Long> recipeIds) {
        List<RecipeRefactoredDTO> addedRecipes = recipeService.addLikedRecipes(authentication, recipeIds);
        return !addedRecipes.isEmpty()
                ? ResponseEntity.ok(addedRecipes)
                : ResponseEntity.badRequest().build();
    }

    //TODO: update'owanie przepisu
    //TODO: wymyślić usuwanie przepisów - jest problematyczne, ponieważ inni użytkownicy mogą mieć do nich odniesienia
    // pomysł:
    // - usunąć przepisy które nie są z nikim innym powiązane
    // - przepisy z powiązaniami ustawić na niewidoczne dla nikogo na frontendzie, więc dla użytkownika będą usunięte
    // - wszystkie przepisy ustawiac na niewidoczne

}
