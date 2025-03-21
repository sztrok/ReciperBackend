package com.eat.it.eatit.backend.controller.global;

import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.auth.request.AccountCreationRequest;
import com.eat.it.eatit.backend.dto.auth.request.LoginRequest;
import com.eat.it.eatit.backend.dto.auth.request.RefreshTokenRequest;
import com.eat.it.eatit.backend.dto.auth.response.LoginAndRegisterResponse;
import com.eat.it.eatit.backend.dto.auth.response.TokenResponse;
import com.eat.it.eatit.backend.enums.AccountRole;
import com.eat.it.eatit.backend.security.service.JwtTokenProvider;
import com.eat.it.eatit.backend.service.general.account.AccountService;
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
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    AccountService accountService;
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(AccountService accountService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
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
        String newAccessToken = jwtTokenProvider.generateAccessToken(accountDTO.getUsername(), List.of(AccountRole.ROLE_USER.toString()));
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(accountDTO.getUsername());
        return accountDTO.getId() != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(new LoginAndRegisterResponse(accountDTO, newAccessToken, newRefreshToken))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("/login")
    @Operation(summary = "Log in to application")
    @ApiResponse(responseCode = "200", description = "User logged in successfully")
    public ResponseEntity<LoginAndRegisterResponse> login(@RequestBody LoginRequest loginRequest) {
        log.info("Login request for user: {}", loginRequest.getUsername());

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        AccountDTO accountDTO = accountService.getAccount(loginRequest.getUsername());
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        String accessToken = jwtTokenProvider.generateAccessToken(authentication.getName(), roles);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication.getName());

        return ResponseEntity.ok(new LoginAndRegisterResponse(accountDTO, accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Generate new access token")
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
        return ResponseEntity.ok(new TokenResponse(newAccessToken, null));
    }


}
