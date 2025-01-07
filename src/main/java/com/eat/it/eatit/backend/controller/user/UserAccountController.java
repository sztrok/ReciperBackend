package com.eat.it.eatit.backend.controller.user;

import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.RecipeDTO;
import com.eat.it.eatit.backend.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('ROLE_USER')")
@RequestMapping("/api/v1/user/account")
public class UserAccountController {

    AccountService accountService;

    @Autowired
    public UserAccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account_recipes")
    @Operation(summary = "Get account recipes", description = "Returns list of recipes assigned to this account, or empty list if account has no assigned recipes")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved recipes assigned to Account")
    public ResponseEntity<List<RecipeDTO>> getAccountRecipes(Authentication authentication) {
        Long userId = accountService.getAccountIdByName(authentication.getName());
        return ResponseEntity.ok(accountService.getAccountRecipes(userId));
    }

    @GetMapping("/liked_recipes")
    @Operation(summary = "Get liked recipes", description = "Returns list of recipes liked by this account, or empty list if account has no liked recipes")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved recipes liked by this Account")
    public ResponseEntity<List<RecipeDTO>> getLikedRecipes(Authentication authentication) {
        Long userId = accountService.getAccountIdByName(authentication.getName());
        return ResponseEntity.ok(accountService.getLikedRecipes(userId));
    }

    @PutMapping()
    @Operation(summary = "Update account", description = "Updates an existing account identified by its ID based on the provided AccountDTO.")
    @ApiResponse(responseCode = "200", description = "Successfully updated the account.")
    @ApiResponse(responseCode = "400", description = "Invalid account ID or account not found.")
    public ResponseEntity<AccountDTO> updateAccount(Authentication authentication, @RequestBody AccountDTO accountDTO) {
        Long userId = accountService.getAccountIdByName(authentication.getName());
        AccountDTO account = accountService.updateAccountById(userId, accountDTO);
        return account != null
                ? ResponseEntity.ok(account)
                : ResponseEntity.badRequest().build();
    }

    @PutMapping("/account_recipes")
    @Operation(summary = "Add recipes to an account", description = "Adds a list of recipes to an existing account identified by its ID.")
    @ApiResponse(responseCode = "200", description = "Successfully added recipes to the account.")
    @ApiResponse(responseCode = "400", description = "Account not found.")
    public ResponseEntity<List<RecipeDTO>> addRecipesToAccount(Authentication authentication, @RequestBody List<RecipeDTO> recipeDTOS) {
        Long userId = accountService.getAccountIdByName(authentication.getName());
        List<RecipeDTO> addedRecipes = accountService.addRecipesToAccount(userId, recipeDTOS);
        return !addedRecipes.isEmpty()
                ? ResponseEntity.ok(addedRecipes)
                : ResponseEntity.badRequest().build();
    }

    @PutMapping("/liked_recipes")
    @Operation(summary = "Add liked recipes", description = "Adds a list of liked recipes to an existing account.")
    @ApiResponse(responseCode = "200", description = "Successfully added liked recipes.")
    @ApiResponse(responseCode = "400", description = "Account not found.")
    public ResponseEntity<List<RecipeDTO>> addLikedRecipes(Authentication authentication, @RequestBody List<RecipeDTO> recipeDTOS) {
        Long userId = accountService.getAccountIdByName(authentication.getName());
        List<RecipeDTO> addedRecipes = accountService.addLikedRecipes(userId, recipeDTOS);
        return !addedRecipes.isEmpty()
                ? ResponseEntity.ok(addedRecipes)
                : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/account_recipe")
    @Operation(summary = "Delete account recipes", description = "Delete recipe assigned to this account.")
    @ApiResponse(responseCode = "200", description = "Successfully deleted account recipe.")
    @ApiResponse(responseCode = "400", description = "Account or recipe not found.")
    public ResponseEntity<Void> deleteAccountRecipe(Authentication authentication, @RequestBody Long recipeId) {
        Long userId = accountService.getAccountIdByName(authentication.getName());
        return accountService.deleteAccountRecipe(userId, recipeId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/liked_recipes")
    @Operation(summary = "Delete liked recipes", description = "Delete recipe from liked list.")
    @ApiResponse(responseCode = "200", description = "Successfully deleted liked recipe.")
    @ApiResponse(responseCode = "400", description = "Account not found.")
    public ResponseEntity<Void> deleteLikedRecipe(Authentication authentication, @RequestBody Long recipeId) {
        Long userId = accountService.getAccountIdByName(authentication.getName());
        return accountService.deleteLikedRecipe(userId, recipeId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }

    //TODO: dodać więcej endpointów
}
