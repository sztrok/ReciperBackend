package com.eat.it.eatit.backend.dto.simple;

import com.eat.it.eatit.backend.enums.AccountRole;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class AccountSimpleDTO {

    @Nullable
    private Long id;
    private String username;
    private String mail;
    private Long fridgeId;
    private Boolean premium;
    private Set<AccountRole> accountRoles;

    public AccountSimpleDTO(@Nullable Long id, String username, String mail, Long fridgeId, Boolean premium, Set<AccountRole> accountRoles) {
        this.id = id;
        this.username = username;
        this.mail = mail;
        this.fridgeId = fridgeId;
        this.premium = premium;
        this.accountRoles = accountRoles;
    }

}
