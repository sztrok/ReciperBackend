package com.eat.it.eatit.backend.recipe;

import com.eat.it.eatit.backend.cookware.CookwareDTO;
import com.eat.it.eatit.backend.item.ItemDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class RecipeDTO {

    private String name;
    private Long ownerId;
    private String description;
    private Set<ItemDTO> items;
    private Set<CookwareDTO> cookware;
    private Integer totalCalories;

    public RecipeDTO(String name, Long ownerId, String description, Set<ItemDTO> items, Set<CookwareDTO> cookware, Integer totalCalories) {
        this.name = name;
        this.ownerId = ownerId;
        this.description = description;
        this.items = items;
        this.cookware = cookware;
        this.totalCalories = totalCalories;
    }

    public RecipeDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
