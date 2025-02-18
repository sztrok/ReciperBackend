package com.eat.it.eatit.backend.dto.refactored.account;

import com.eat.it.eatit.backend.dto.refactored.recipe.RecipeRefactoredDTO;
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
    private List<RecipeRefactoredDTO> accountRecipes = new ArrayList<>();
    private List<RecipeRefactoredDTO> likedRecipes = new ArrayList<>();
    private Set<AccountRole> accountRoles;
}
