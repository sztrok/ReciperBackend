package com.eat.it.eatit.backend.dto.account;

import com.eat.it.eatit.backend.enums.AccountRole;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountSimpleDTO {

    @Nullable
    private String username;
    private String mail;
    private Boolean premium;
    private Set<AccountRole> accountRoles;
}
