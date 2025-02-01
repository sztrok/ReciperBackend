package com.eat.it.eatit.backend.dto.simple;

import com.eat.it.eatit.backend.dto.recipe.RecipeSimpleDTO;
import com.eat.it.eatit.backend.enums.AccountRole;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountSimpleDTO {

    @Nullable
    private Long id;
    private String username;
    private String mail;
    private Boolean premium;
    private List<RecipeSimpleDTO> accountRecipes = new ArrayList<>();
    private List<RecipeSimpleDTO> likedRecipes = new ArrayList<>();
    private Set<AccountRole> accountRoles;

    public AccountSimpleDTO(@Nullable Long id, String username, String mail, Boolean premium, Set<AccountRole> accountRoles) {
        this.id = id;
        this.username = username;
        this.mail = mail;
        this.premium = premium;
        this.accountRoles = accountRoles;
    }

}
