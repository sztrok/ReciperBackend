package com.eat.it.eatit.backend.controller.support;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/support/account")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class SupportAccountController {

    AccountService accountService;

    @Autowired
    public SupportAccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    // ADMIN / SUPPORT
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Retrieve account by ID", description = "Retrieves an account by its ID and returns it as an AccountDTO.")
    @GetMapping(value = "/{id}")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved account by ID.")
    @ApiResponse(responseCode = "400", description = "Invalid account ID or account not found.")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
        AccountDTO account = accountService.getAccountById(id);
        return account != null
                ? ResponseEntity.ok(account)
                : ResponseEntity.badRequest().build();
    }
    // ADMIN / SUPPORT
    @Operation(summary = "Retrieve all accounts", description = "Retrieves all accounts and returns them as a list of AccountSimpleDTO.")
    @GetMapping(value = "all")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all accounts.")
    public ResponseEntity<List<AccountSimpleDTO>> getAllAccounts() {
        List<AccountSimpleDTO> accounts = accountService.getAllAccounts()
                .stream()
                .map(AccountSimpleMapper::toSimple)
                .toList();
        return ResponseEntity.ok(accounts);
    }
    // ADMIN / SUPPORT
    @Operation(summary = "Add a new account", description = "Adds a new account based on the provided AccountDTO.")
    @PostMapping(consumes = "application/json")
    @ApiResponse(responseCode = "200", description = "Successfully added a new account.")
    @ApiResponse(responseCode = "400", description = "Invalid account details.")
    public ResponseEntity<AccountDTO> addNewAccount(@RequestBody AccountDTO accountDTO) {
        AccountDTO account = accountService.addNewAccount(accountDTO);
        return ResponseEntity.ok(account);
    }
    // ADMIN
    @Operation(summary = "Delete an account by ID", description = "Deletes an account identified by its ID.")
    @DeleteMapping(value = "/{id}")
    @ApiResponse(responseCode = "200", description = "Successfully deleted the account.")
    @ApiResponse(responseCode = "400", description = "Invalid account ID or account not found.")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        return accountService.deleteAccountById(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }
    // ALL, ale user moze tylko swoje updateowac
    @Operation(summary = "Update an account by ID", description = "Updates an existing account identified by its ID based on the provided AccountDTO.")
    @PutMapping(value = "/{id}")
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
    @PutMapping(value = "/{id}/recipes")
    @ApiResponse(responseCode = "200", description = "Successfully added recipes to the account.")
    @ApiResponse(responseCode = "400", description = "Invalid account ID or account not found.")
    public ResponseEntity<List<RecipeDTO>> addRecipesToAccount(@PathVariable Long id, @RequestBody List<RecipeDTO> recipeDTOS) {
        List<RecipeDTO> addedRecipes = accountService.addRecipesToAccount(id, recipeDTOS);
        return addedRecipes != null
                ? ResponseEntity.ok(addedRecipes)
                : ResponseEntity.badRequest().build();
    }
}
