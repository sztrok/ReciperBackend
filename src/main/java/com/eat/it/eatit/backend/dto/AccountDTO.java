package com.eat.it.eatit.backend.dto;

import com.eat.it.eatit.backend.enums.AccountRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class AccountDTO {

    private String username;
    private String mail;
    private String password;
    private FridgeDTO fridge;
    private Set<RecipeDTO> recipes = new HashSet<>();
    private Boolean premium;
    private Set<AccountRole> accountRoles = new HashSet<>();

    public AccountDTO(String username, String mail, String password, FridgeDTO fridge, Set<RecipeDTO> recipes, Boolean premium, Set<AccountRole> accountRoles) {
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.fridge = fridge;
        this.recipes = recipes;
        this.premium = premium;
        this.accountRoles = accountRoles;
    }
}
