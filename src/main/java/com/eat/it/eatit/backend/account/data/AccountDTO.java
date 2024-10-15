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
    private String password;
    private FridgeDTO fridge;
    private Set<RecipeDTO> recipes;
    private Boolean premium;

    public AccountDTO(String username, String mail, String password, FridgeDTO fridge, Set<RecipeDTO> recipes, Boolean premium) {
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.fridge = fridge;
        this.recipes = recipes;
        this.premium = premium;
    }
}
