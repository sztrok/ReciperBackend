package com.eat.it.eatit.backend.controller.user.account;

import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.service.user.account.UserAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    // ACCOUNT DATA
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
}
