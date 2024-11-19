package com.eat.it.eatit.backend.controller;

import com.eat.it.eatit.backend.service.AccountService;
import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.RecipeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/test")
    public String test() {
        return "Success";
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
        AccountDTO account = accountService.getAccountById(id);
        if (account == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(account);
    }

    @GetMapping(value = "all")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @PostMapping(value = "/new", consumes = "application/json")
    public ResponseEntity<AccountDTO> addNewAccount(@RequestBody AccountDTO accountDTO) {
        AccountDTO account = accountService.addNewAccount(accountDTO);
        return ResponseEntity.ok(account);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        return accountService.deleteAccountById(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id, @RequestBody AccountDTO accountDTO) {
        AccountDTO account = accountService.updateAccountById(id, accountDTO);
        return account != null
                ? ResponseEntity.ok(account)
                : ResponseEntity.badRequest().build();
    }

    @PutMapping(value = "/recipe/{id}")
    public ResponseEntity<Set<RecipeDTO>> addRecipesToAccount(@PathVariable Long id, @RequestBody Set<RecipeDTO> recipeDTOS) {
        Set<RecipeDTO> addedRecipes = accountService.addRecipesToAccount(id, recipeDTOS);

        if (addedRecipes == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(addedRecipes);
    }
}
