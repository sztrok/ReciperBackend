package com.eat.it.eatit.backend.recipe.data;

import com.eat.it.eatit.backend.cookware.data.CookwareDTO;
import com.eat.it.eatit.backend.item.data.ItemDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class RecipeDTO {

    private String name;
    private String description;
    private Set<ItemDTO> items;
    private Set<CookwareDTO> cookware;
    private Integer totalCalories;

    public RecipeDTO(String name, String description, Set<ItemDTO> items, Set<CookwareDTO> cookware, Integer totalCalories) {
        this.name = name;
        this.description = description;
        this.items = items;
        this.cookware = cookware;
        this.totalCalories = totalCalories;
    }

}
