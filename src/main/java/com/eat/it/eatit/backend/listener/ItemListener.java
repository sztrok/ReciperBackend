package com.eat.it.eatit.backend.listener;

import com.eat.it.eatit.backend.data.Item;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemListener {

    @PreUpdate
    @PrePersist
    public void calculateTotalCaloriesOfItem(Item item) {
        log.info("Calculating total calories for item with ID: {}", item.getId());
        Double fats = item.getFats();
        Double proteins = item.getProteins();
        Double carbs = item.getCarbs();
        if (fats != null && proteins != null && carbs != null) {
            Double totalCalories = fats * 9 + proteins * 4 + carbs * 4;
            item.setCaloriesPer100g(totalCalories);
        }
    }
}
