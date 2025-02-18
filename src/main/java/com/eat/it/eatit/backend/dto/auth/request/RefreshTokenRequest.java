package com.eat.it.eatit.backend.dto.auth.request;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
