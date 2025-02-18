package com.eat.it.eatit.backend.dto.auth.response;

import com.eat.it.eatit.backend.dto.refactored.account.AccountSimpleRefactoredDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginAndRegisterResponse {
    private AccountSimpleRefactoredDTO account;
    private String accessToken;
    private String refreshToken;
}
