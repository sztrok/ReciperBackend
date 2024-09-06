package com.eat.it.eatit.backend.dto;

import com.eat.it.eatit.backend.data.Recipe;
import lombok.Data;

import java.util.List;

@Data
public class AccountDTO {
    private String username;
    private String mail;
    private FridgeDTO fridge;
    private List<Recipe> recipes;
    private boolean premium;

    public AccountDTO(String username, String mail, FridgeDTO fridge, List<Recipe> recipes, boolean premium) {
        this.username = username;
        this.mail = mail;
        this.fridge = fridge;
        this.recipes = recipes;
        this.premium = premium;
    }
}
