package com.eat.it.eatit.backend.account.api;

import com.eat.it.eatit.backend.account.data.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/test")
    public String test() {
        return "Success";
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<AccountDTO> addNewAccount(@RequestBody AccountDTO accountDTO) {
        return accountService.addNewAccount(accountDTO);
    }
}
