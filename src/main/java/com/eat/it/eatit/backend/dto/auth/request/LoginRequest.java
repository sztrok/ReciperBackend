package com.eat.it.eatit.backend.dto.auth.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
