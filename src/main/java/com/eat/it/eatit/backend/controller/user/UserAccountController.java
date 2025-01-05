package com.eat.it.eatit.backend.controller.user;

import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.RecipeDTO;
import com.eat.it.eatit.backend.dto.simple.AccountSimpleDTO;
import com.eat.it.eatit.backend.mapper.simple.AccountSimpleMapper;
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
@RequestMapping("/api/v1/user/account")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class UserAccountController {

    AccountService accountService;

    @Autowired
    public UserAccountController(AccountService accountService) {
        this.accountService = accountService;
    }

//    @GetMapping("/account_recipes")
//    public ResponseEntity<List<AccountSimpleDTO>> getAccountRecipes(Authentication authentication) {
//        return accountService.
//    }
//
    // ALL, ale user moze tylko swoje updateowac
    @Operation(summary = "Update an account by ID", description = "Updates an existing account identified by its ID based on the provided AccountDTO.")
    @PutMapping(value = "/")
    @ApiResponse(responseCode = "200", description = "Successfully updated the account.")
    @ApiResponse(responseCode = "400", description = "Invalid account ID or account not found.")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id, @RequestBody AccountDTO accountDTO) {
        AccountDTO account = accountService.updateAccountById(id, accountDTO);
        return account != null
                ? ResponseEntity.ok(account)
                : ResponseEntity.badRequest().build();
    }
    // ALL ale ograniczenia ze user moze tylko swoje
    @Operation(summary = "Add recipes to an account", description = "Adds a list of recipes to an existing account identified by its ID.")
    @PutMapping(value = "/account_recipes")
    @ApiResponse(responseCode = "200", description = "Successfully added recipes to the account.")
    @ApiResponse(responseCode = "400", description = "Invalid account ID or account not found.")
    public ResponseEntity<List<RecipeDTO>> addRecipesToAccount(Authentication authentication, @RequestBody List<RecipeDTO> recipeDTOS) {
        AccountDTO account = accountService.getAccountByName(authentication.getName());
        List<RecipeDTO> addedRecipes = accountService.addRecipesToAccount(account.getId(), recipeDTOS);
        return addedRecipes != null
                ? ResponseEntity.ok(addedRecipes)
                : ResponseEntity.badRequest().build();
    }
}
