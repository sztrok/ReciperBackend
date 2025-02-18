package com.eat.it.eatit.backend.dto;

import com.eat.it.eatit.backend.enums.AccountRole;
import jakarta.annotation.Nullable;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    @Nullable
    private Long id;
    private String username;
    private String mail;
    private String password;
    private FridgeDTO fridge;
    private Boolean premium;
    private Set<AccountRole> accountRoles = new HashSet<>();

}
