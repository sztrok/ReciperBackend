package com.eat.it.eatit.backend.account.api;

import com.eat.it.eatit.backend.account.data.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    AccountHandler accountHandler;

    @Autowired
    public AccountController(AccountHandler accountHandler) {
        this.accountHandler = accountHandler;
    }

    @GetMapping("/test")
    public String test() {
        return "Success";
    }

    @GetMapping("/get/id/{id}")
    public AccountDTO getAccountById(@PathVariable Long id) {
        return accountHandler.getAccountById(id);
    }

    @GetMapping("/get_all")
    public List<AccountDTO> getAllAccounts() {
        return accountHandler.getAllAccounts();
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<AccountDTO> addNewAccount(@RequestBody AccountDTO accountDTO) {
        return accountHandler.addNewAccount(accountDTO);
    }
}
