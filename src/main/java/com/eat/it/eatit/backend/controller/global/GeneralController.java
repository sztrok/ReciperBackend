package com.eat.it.eatit.backend.controller.global;

import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.simple.AccountCreationRequest;
import com.eat.it.eatit.backend.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/global/general")
public class GeneralController {

    AccountService accountService;

    @PostMapping("register")
    public ResponseEntity<AccountDTO> register(@Valid @RequestBody AccountCreationRequest account) {
        AccountDTO accountDTO = accountService.createAccount(account);
        return accountDTO.getId() != null
                ? new ResponseEntity<>(accountDTO, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/profile")
    public ResponseEntity<AccountDTO> getProfile(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(accountService.getAllAccounts().stream().filter(acc -> acc.getUsername().equals(username)).findFirst().orElse(new AccountDTO()));
    }

}
