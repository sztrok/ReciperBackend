package com.eat.it.eatit.backend.dto;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemInRecipeDTO {

    @Nullable
    private Long id;
    private Long recipeId;
    private ItemDTO item;
    private Double amount;

    public ItemInRecipeDTO(@Nullable Long id, Long recipeId, ItemDTO item, Double amount) {
        this.id = id;
        this.recipeId = recipeId;
        this.item = item;
        this.amount = amount;
    }

    public ItemInRecipeDTO(Long recipeId, ItemDTO item, Double amount) {
        this.recipeId = recipeId;
        this.item = item;
        this.amount = amount;
    }
}
