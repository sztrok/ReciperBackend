package com.eat.it.eatit.backend.controller.global;

import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.simple.AccountCreationRequest;
import com.eat.it.eatit.backend.dto.simple.AuthenticationResponse;
import com.eat.it.eatit.backend.dto.simple.LoginRequest;
import com.eat.it.eatit.backend.dto.simple.RefreshTokenRequest;
import com.eat.it.eatit.backend.security.service.JwtTokenProvider;
import com.eat.it.eatit.backend.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/global/general")
public class GeneralController {

    private final AuthenticationManager authenticationManager;
    AccountService accountService;
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    public GeneralController(AccountService accountService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.accountService = accountService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    @Operation(summary = "Register new account", description = "Creates new account based on account creation request.")
    @ApiResponse(responseCode = "200", description = "Account registered successfully")
    @ApiResponse(responseCode = "500", description = "Error while registering an account")
    public ResponseEntity<AccountDTO> register(@Valid @RequestBody AccountCreationRequest account) {
        log.info("Registering account username: {} email: {}", account.getUsername(), account.getEmail());
        AccountDTO accountDTO = accountService.createAccount(account);
        return accountDTO.getId() != null
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/profile")
    @Operation(summary = "Get profile information", description = "Retrieves information about user profile.")
    @ApiResponse(responseCode = "200", description = "Account registered successfully")
    public ResponseEntity<AccountDTO> getProfile(Authentication authentication) {
        log.info("Get profile info for user {}", authentication.getName());
        String username = authentication.getName();
        return ResponseEntity.ok(accountService.getAllAccounts().stream().filter(acc -> acc.getUsername().equals(username)).findFirst().orElse(new AccountDTO()));
    }

    @PostMapping("/login")
    @Operation(summary = "Log in to application")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        log.info("Login request for user: {}", loginRequest.getUsername());

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        String accessToken = jwtTokenProvider.generateAccessToken(authentication.getName(), roles);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication.getName());

        return ResponseEntity.ok(new AuthenticationResponse(accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        log.info("Refresh token: {}", refreshToken);
        if(!jwtTokenProvider.validateToken(refreshToken)) {
            log.info("Refresh token not valid");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);

        // Wygenerowanie nowych tokenów
        List<String> roles = jwtTokenProvider.getRolesFromToken(refreshToken);
        String newAccessToken = jwtTokenProvider.generateAccessToken(username, roles);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(username);

        return ResponseEntity.ok(new AuthenticationResponse(newAccessToken, newRefreshToken));
    }

}
