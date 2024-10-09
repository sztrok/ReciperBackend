package com.eat.it.eatit.backend.cookware.data;

import com.eat.it.eatit.backend.recipe.data.Recipe;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class CookwareDTO {

    private String name;
    private Set<Recipe> recipesContainingCookware;

    public CookwareDTO(String name) {
        this.name = name;
    }

    public CookwareDTO(String name, Set<Recipe> recipesContainingCookware) {
        this.name = name;
        this.recipesContainingCookware = recipesContainingCookware;
    }
}
