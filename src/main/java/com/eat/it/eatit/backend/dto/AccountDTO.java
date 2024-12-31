package com.eat.it.eatit.backend.dto;

import com.eat.it.eatit.backend.enums.AccountRole;
import jakarta.annotation.Nullable;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class AccountDTO {

    @Nullable
    private Long id;
    private String username;
    private String mail;
    private String password;
    private FridgeDTO fridge;
    private List<RecipeDTO> accountRecipes = new ArrayList<>();
    private List<RecipeDTO> likedRecipes = new ArrayList<>();
    private Boolean premium;
    private Set<AccountRole> accountRoles = new HashSet<>();

    public AccountDTO(@Nullable Long id, String username, String mail, String password, FridgeDTO fridge, List<RecipeDTO> accountRecipes, List<RecipeDTO> likedRecipes, Boolean premium, Set<AccountRole> accountRoles) {
        this.id = id;
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.fridge = fridge;
        this.accountRecipes = accountRecipes;
        this.likedRecipes = likedRecipes;
        this.premium = premium;
        this.accountRoles = accountRoles;
    }
}
