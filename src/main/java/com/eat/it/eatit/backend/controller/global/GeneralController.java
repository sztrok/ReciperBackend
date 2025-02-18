package com.eat.it.eatit.backend.controller.global;

import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.auth.request.AccessTokenRequest;
import com.eat.it.eatit.backend.dto.auth.request.AccountCreationRequest;
import com.eat.it.eatit.backend.dto.auth.request.LoginRequest;
import com.eat.it.eatit.backend.dto.auth.request.RefreshTokenRequest;
import com.eat.it.eatit.backend.dto.auth.response.LoginAndRegisterResponse;
import com.eat.it.eatit.backend.dto.auth.response.TokenResponse;
import com.eat.it.eatit.backend.dto.refactored.account.AccountSimpleRefactoredDTO;
import com.eat.it.eatit.backend.enums.AccountRole;
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
    public ResponseEntity<LoginAndRegisterResponse> register(@Valid @RequestBody AccountCreationRequest account) {
        log.info("Registering account username: {} email: {}", account.getUsername(), account.getEmail());
        AccountDTO accountDTO = accountService.createAccount(account);
        AccountSimpleRefactoredDTO accountSimple = accountService.getAccountSimple(accountDTO.getUsername());
        String newAccessToken = jwtTokenProvider.generateAccessToken(accountDTO.getUsername(), List.of(AccountRole.ROLE_USER.toString()));
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(accountDTO.getUsername());
        return accountDTO.getId() != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(new LoginAndRegisterResponse(accountSimple, newAccessToken, newRefreshToken))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/profile")
    @Operation(summary = "Get profile information", description = "Retrieves information about user profile.")
    @ApiResponse(responseCode = "200", description = "Account registered successfully")
    public ResponseEntity<AccountSimpleRefactoredDTO> getProfile(Authentication authentication) {
        log.info("Get profile info for user {}", authentication.getName());
        String username = authentication.getName();
        return ResponseEntity.ok(accountService.getAllAccountsSimple().stream().filter(acc -> acc.getUsername().equals(username)).findFirst().orElse(new AccountSimpleRefactoredDTO()));
    }

    @PostMapping("/login")
    @Operation(summary = "Log in to application")
    @ApiResponse(responseCode = "200", description = "User logged in successfully")
    public ResponseEntity<LoginAndRegisterResponse> login(@RequestBody LoginRequest loginRequest) {
        log.info("Login request for user: {}", loginRequest.getUsername());

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        AccountSimpleRefactoredDTO accountSimple = accountService.getAccountSimple(loginRequest.getUsername());
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String accessToken = jwtTokenProvider.generateAccessToken(authentication.getName(), roles);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication.getName());
        return ResponseEntity.ok(new LoginAndRegisterResponse(accountSimple, accessToken, refreshToken));
    }

    @PostMapping("/tokens/refreshToken")
    @Operation(summary = "Validate refresh token")
    @ApiResponse(responseCode = "200", description = "Refresh token is valid")
    @ApiResponse(responseCode = "401", description = "Refresh token is not valid")
    public ResponseEntity<Void> checkIfRefreshTokenIsValid(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tokens/accessToken")
    @Operation(summary = "Validate access token")
    @ApiResponse(responseCode = "200", description = "Access token is valid")
    @ApiResponse(responseCode = "401", description = "Access token is not valid")
    public ResponseEntity<Void> checkIfAccessTokenIsValid(@RequestBody AccessTokenRequest accessTokenRequest) {
        String accessToken = accessTokenRequest.getAccessToken();
        if (!jwtTokenProvider.validateToken(accessToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tokens/accessToken/new")
    @Operation(summary = "Generate new access token")
    @ApiResponse(responseCode = "200", description = "Successfully generated new access token")
    @ApiResponse(responseCode = "401", description = "Refresh token is not valid")
    public ResponseEntity<TokenResponse> getNewAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
        List<String> roles = accountService.getAccountRoles(username);
        String newAccessToken = jwtTokenProvider.generateAccessToken(username, roles);
        return ResponseEntity.ok(new TokenResponse(newAccessToken, null));
    }

    @PostMapping("/tokens")
    @Operation(summary = "Generate new tokens")
    @ApiResponse(responseCode = "200", description = "Successfully generated new tokens")
    @ApiResponse(responseCode = "401", description = "Refresh token is not valid")
    public ResponseEntity<TokenResponse> getNewTokens(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        log.info("Refresh token: {}", refreshToken);
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            log.info("Refresh token not valid");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);

        List<String> roles = accountService.getAccountRoles(username);
        String newAccessToken = jwtTokenProvider.generateAccessToken(username, roles);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(username);

        return ResponseEntity.ok(new TokenResponse(newAccessToken, newRefreshToken));
    }


}
