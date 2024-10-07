package com.eat.it.eatit.backend.account.data;

import com.eat.it.eatit.backend.fridge.data.FridgeDTO;
import com.eat.it.eatit.backend.recipe.data.RecipeDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class AccountDTO {

    private String username;
    private String mail;
    private FridgeDTO fridge;
    private Set<RecipeDTO> recipes;
    private Boolean premium;

    public AccountDTO(String username, String mail, FridgeDTO fridge, Set<RecipeDTO> recipes, Boolean premium) {
        this.username = username;
        this.mail = mail;
        this.fridge = fridge;
        this.recipes = recipes;
        this.premium = premium;
    }
}
