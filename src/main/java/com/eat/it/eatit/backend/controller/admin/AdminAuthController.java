package com.eat.it.eatit.backend.controller.admin;

import com.eat.it.eatit.backend.dto.auth.request.LoginRequest;
import com.eat.it.eatit.backend.enums.AccountRole;
import com.eat.it.eatit.backend.enums.ResponseCookieType;
import com.eat.it.eatit.backend.security.service.JwtTokenProvider;
import com.eat.it.eatit.backend.service.general.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/auth")
public class AdminAuthController {
    private final AuthenticationManager authenticationManager;
    AccountService accountService;
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AdminAuthController(AuthenticationManager authenticationManager, AccountService accountService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.accountService = accountService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    @Operation(summary = "Login to admin dashboard")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        log.info("Admin login request: {}", loginRequest);

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        if (!roles.contains(AccountRole.ROLE_ADMIN.toString())) {
            return ResponseEntity.status(401).build();
        }

        String accessToken = jwtTokenProvider.generateAccessToken(authentication.getName(), roles);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication.getName());

        return getResponseWithCookiesInHeader(response, accessToken, refreshToken);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh tokens")
    public ResponseEntity<?> getNewTokens(@CookieValue(name = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {
        if (refreshToken == null || !jwtTokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(401).build();
        }
        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
        List<String> roles = accountService.getAccountRoles(username);
        String newAccessToken = jwtTokenProvider.generateAccessToken(username, roles);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(username);

        return getResponseWithCookiesInHeader(response, newAccessToken, newRefreshToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        ResponseCookie deleteAccessToken = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        ResponseCookie deleteRefreshToken = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, deleteAccessToken.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, deleteRefreshToken.toString());

        return ResponseEntity.ok().build();
    }

    @NotNull
    private ResponseEntity<?> getResponseWithCookiesInHeader(HttpServletResponse response, String accessToken, String refreshToken) {
        ResponseCookie accessCookie = accountService.getResponseCookie(ResponseCookieType.ACCESS_TOKEN, accessToken);
        ResponseCookie refreshCookie = accountService.getResponseCookie(ResponseCookieType.REFRESH_TOKEN, refreshToken);

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return ResponseEntity.ok().build();
    }

}
