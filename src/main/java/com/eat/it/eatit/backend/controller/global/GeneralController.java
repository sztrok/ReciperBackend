package com.eat.it.eatit.backend.controller.global;

import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.simple.AccountCreationRequest;
import com.eat.it.eatit.backend.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Register new account", description = "Creates new account based on account creation request.")
    @ApiResponse(responseCode = "200", description = "Account registered successfully")
    @ApiResponse(responseCode = "500", description = "Error while registering an account")
    public ResponseEntity<AccountDTO> register(@Valid @RequestBody AccountCreationRequest account) {
        AccountDTO accountDTO = accountService.createAccount(account);
        return accountDTO.getId() != null
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/profile")
    @Operation(summary = "Get profile information", description = "Retrieves information about user profile.")
    @ApiResponse(responseCode = "200", description = "Account registered successfully")
    public ResponseEntity<AccountDTO> getProfile(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(accountService.getAllAccounts().stream().filter(acc -> acc.getUsername().equals(username)).findFirst().orElse(new AccountDTO()));
    }

}
