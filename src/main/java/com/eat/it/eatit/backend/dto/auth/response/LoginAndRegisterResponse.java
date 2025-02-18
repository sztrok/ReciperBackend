package com.eat.it.eatit.backend.dto.auth.response;

import com.eat.it.eatit.backend.dto.AccountDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginAndRegisterResponse {
    private AccountDTO account;
    private String accessToken;
    private String refreshToken;
}
