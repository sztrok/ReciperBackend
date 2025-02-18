package com.eat.it.eatit.backend.dto.account;

import com.eat.it.eatit.backend.dto.recipe.RecipeDTO;
import com.eat.it.eatit.backend.enums.AccountRole;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountSimpleRefactoredDTO {

    @Nullable
    private Long id;
    private String username;
    private String mail;
    private Boolean premium;
    private List<RecipeDTO> accountRecipes = new ArrayList<>();
    private List<RecipeDTO> likedRecipes = new ArrayList<>();
    private Set<AccountRole> accountRoles;
}
