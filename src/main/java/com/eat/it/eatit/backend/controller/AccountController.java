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

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @GetMapping(value = "/get_all")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<AccountDTO> addNewAccount(@RequestBody AccountDTO accountDTO) {
        return accountService.addNewAccount(accountDTO);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<AccountDTO> deleteAccount(@PathVariable Long id) {
        return accountService.deleteAccountById(id);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id, @RequestBody AccountDTO accountDTO) {
        return accountService.updateAccountById(id, accountDTO);
    }

    @PutMapping(value = "/add_recipes/{id}/")
    public ResponseEntity<Set<RecipeDTO>> addRecipesToAccount(@PathVariable Long id, @RequestBody Set<RecipeDTO> recipeDTOS) {
        return accountService.addRecipesToAccount(id, recipeDTOS);
    }

}
