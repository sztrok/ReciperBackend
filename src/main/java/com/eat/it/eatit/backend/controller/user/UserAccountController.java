package com.eat.it.eatit.backend.controller.user;

import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.RecipeDTO;
import com.eat.it.eatit.backend.dto.simple.RecipeSimpleDTO;
import com.eat.it.eatit.backend.service.RecipeService;
import com.eat.it.eatit.backend.service.user.UserAccountService;
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
@RequestMapping("/api/v1/user/account")
public class UserAccountController {

    UserAccountService accountService;

    @Autowired
    public UserAccountController(UserAccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account_recipes")
    @Operation(summary = "Get account recipes", description = "Returns list of recipes assigned to this account, or empty list if account has no assigned recipes")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved recipes assigned to Account")
    public ResponseEntity<List<RecipeSimpleDTO>> getAccountRecipes(Authentication authentication) {
        log.info("Get account recipes");
        return ResponseEntity.ok(accountService.getAccountRecipes(authentication));
    }

    @GetMapping("/liked_recipes")
    @Operation(summary = "Get liked recipes", description = "Returns list of recipes liked by this account, or empty list if account has no liked recipes")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved recipes liked by this Account")
    public ResponseEntity<List<RecipeDTO>> getLikedRecipes(Authentication authentication) {
        return ResponseEntity.ok(accountService.getLikedRecipes(authentication));
    }

    @PutMapping()
    @Operation(summary = "Update account", description = "Updates an existing account identified by its ID based on the provided AccountDTO.")
    @ApiResponse(responseCode = "200", description = "Successfully updated the account.")
    @ApiResponse(responseCode = "400", description = "Invalid account ID or account not found.")
    public ResponseEntity<AccountDTO> updateAccount(Authentication authentication, @RequestBody AccountDTO accountDTO) {
        AccountDTO account = accountService.updateAccountById(authentication, accountDTO);
        return account != null
                ? ResponseEntity.ok(account)
                : ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/account_recipes/new", consumes = "application/json")
    @Operation(summary = "Create a new Recipe", description = "Add a new recipe to assigned to account.")
    @ApiResponse(responseCode = "200", description = "Recipe added successfully")
    public ResponseEntity<RecipeDTO> addNewRecipe(Authentication authentication, @RequestBody RecipeDTO recipeDTO) {
        RecipeDTO added = accountService.addNewAccountRecipe(authentication, recipeDTO);
        return ResponseEntity.ok(added);
    }

    @PutMapping("/account_recipes")
    @Operation(summary = "Add recipes to an account", description = "Adds a list of recipes to an existing account identified by its ID.")
    @ApiResponse(responseCode = "200", description = "Successfully added recipes to the account.")
    @ApiResponse(responseCode = "400", description = "Account not found.")
    public ResponseEntity<List<RecipeDTO>> addRecipesToAccount(Authentication authentication, @RequestBody List<RecipeDTO> recipeDTOS) {
        List<RecipeDTO> addedRecipes = accountService.addRecipesToAccount(authentication, recipeDTOS);
        return !addedRecipes.isEmpty()
                ? ResponseEntity.ok(addedRecipes)
                : ResponseEntity.badRequest().build();
    }

    @PutMapping("/liked_recipes")
    @Operation(summary = "Add liked recipes", description = "Adds a list of liked recipes to an existing account.")
    @ApiResponse(responseCode = "200", description = "Successfully added liked recipes.")
    @ApiResponse(responseCode = "400", description = "Account not found.")
    public ResponseEntity<List<RecipeDTO>> addLikedRecipes(Authentication authentication, @RequestBody List<RecipeDTO> recipeDTOS) {
        List<RecipeDTO> addedRecipes = accountService.addLikedRecipes(authentication, recipeDTOS);
        return !addedRecipes.isEmpty()
                ? ResponseEntity.ok(addedRecipes)
                : ResponseEntity.badRequest().build();
    }

    @PutMapping("/account_recipe/{recipeId}")
    @Operation(summary = "Update account Recipe", description = "Update the details of an existing recipe assigned to this account by providing its ID and updated information.")
    @ApiResponse(responseCode = "200", description = "Recipe updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or non-existent recipe ID")
    public ResponseEntity<RecipeDTO> updateRecipe(Authentication authentication, @PathVariable Long recipeId, @RequestBody RecipeDTO recipeDTO) {
        RecipeDTO recipe = accountService.updateAccountRecipeById(authentication, recipeId, recipeDTO);
        return recipe != null
                ? ResponseEntity.ok(recipe)
                : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/account_recipe/{recipeId}")
    @Operation(summary = "Delete account recipes", description = "Delete recipe assigned to this account.")
    @ApiResponse(responseCode = "200", description = "Successfully deleted account recipe.")
    @ApiResponse(responseCode = "400", description = "Account or recipe not found.")
    public ResponseEntity<Void> deleteAccountRecipe(Authentication authentication, @PathVariable Long recipeId) {
        return accountService.deleteAccountRecipe(authentication, recipeId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/liked_recipes/{recipeId}")
    @Operation(summary = "Delete liked recipes", description = "Delete recipe from liked list.")
    @ApiResponse(responseCode = "200", description = "Successfully deleted liked recipe.")
    @ApiResponse(responseCode = "400", description = "Account not found.")
    public ResponseEntity<Void> deleteLikedRecipe(Authentication authentication, @PathVariable Long recipeId) {
        return accountService.deleteLikedRecipe(authentication, recipeId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }

    //TODO: dodać więcej endpointów
}
