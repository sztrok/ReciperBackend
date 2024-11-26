package com.eat.it.eatit.backend.listener;


import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.data.ItemInRecipe;
import com.eat.it.eatit.backend.data.Recipe;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RecipeListener {

    @PreUpdate
    @PrePersist
    public void calculateTotalCaloriesOfRecipe(Recipe recipe) {
        log.info("Calculating total calories for recipe with ID: {}", recipe.getId());
        double totalCalories = 0.0;
        for (ItemInRecipe item : recipe.getItems()) {
            Item i = item.getItem();
            if (i.getCaloriesPer100g() == null) {
                log.warn("Item with ID: {} has no calories per 100g", i.getId());
                continue;
            }
            totalCalories += i.getCaloriesPer100g() * (item.getAmount() / 100.0);
        }
        recipe.setTotalCalories((int) totalCalories);
    }
}
