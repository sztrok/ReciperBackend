package com.eat.it.eatit.backend.dto;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class RecipeDTO {

    @Nullable
    private Long id;
    private String name;
    private String description;
    private Set<ItemInRecipeDTO> items;
    private Set<CookwareDTO> cookware;
    private Integer totalCalories;

    public RecipeDTO(@Nullable Long id, String name, String description, Set<ItemInRecipeDTO> items, Set<CookwareDTO> cookware, Integer totalCalories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.items = items;
        this.cookware = cookware;
        this.totalCalories = totalCalories;
    }

    public RecipeDTO(String name, String description, Set<ItemInRecipeDTO> items, Set<CookwareDTO> cookware, Integer totalCalories) {
        this.name = name;
        this.description = description;
        this.items = items;
        this.cookware = cookware;
        this.totalCalories = totalCalories;
    }
}
