package com.eat.it.eatit.backend.dto;

import lombok.Data;

import java.util.Set;

@Data
public class AccountDTO {
    private String username;
    private String mail;
    private FridgeDTO fridge;
    private Set<RecipeDTO> recipes;
    private boolean premium;

    public AccountDTO(String username, String mail, FridgeDTO fridge, Set<RecipeDTO> recipes, boolean premium) {
        this.username = username;
        this.mail = mail;
        this.fridge = fridge;
        this.recipes = recipes;
        this.premium = premium;
    }
}
