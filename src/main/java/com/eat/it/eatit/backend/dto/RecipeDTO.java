package com.eat.it.eatit.backend.dto;

import com.eat.it.eatit.backend.enums.RecipeDifficulty;
import com.eat.it.eatit.backend.enums.Visibility;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RecipeDTO {

    @Nullable
    private Long id;
    private String name;
    private String description;
    private List<String> simpleSteps;
    private List<String> complexSteps;
    private List<ItemInRecipeDTO> items;
    private List<CookwareDTO> cookware;
    private Integer totalCalories;
    private Visibility visibility = Visibility.PUBLIC;
    private RecipeDifficulty difficulty = RecipeDifficulty.EASY;

    public RecipeDTO(@Nullable Long id, String name, String description, List<String> simpleSteps, List<String> complexSteps, List<ItemInRecipeDTO> items, List<CookwareDTO> cookware, Integer totalCalories, Visibility visibility, RecipeDifficulty difficulty) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.simpleSteps = simpleSteps;
        this.complexSteps = complexSteps;
        this.items = items;
        this.cookware = cookware;
        this.totalCalories = totalCalories;
        this.visibility = visibility;
        this.difficulty = difficulty;
    }

    public RecipeDTO(String name, String description, List<ItemInRecipeDTO> items, List<CookwareDTO> cookware, Integer totalCalories) {
        this.name = name;
        this.description = description;
        this.items = items;
        this.cookware = cookware;
        this.totalCalories = totalCalories;
    }

    public RecipeDTO(String name, String description, List<ItemInRecipeDTO> items, List<CookwareDTO> cookware, Integer totalCalories, Visibility visibility) {
        this.name = name;
        this.description = description;
        this.items = items;
        this.cookware = cookware;
        this.totalCalories = totalCalories;
        this.visibility = visibility;
    }

    public RecipeDTO(String name, String description, List<ItemInRecipeDTO> items, List<CookwareDTO> cookware, Integer totalCalories, Visibility visibility, RecipeDifficulty difficulty) {
        this.name = name;
        this.description = description;
        this.items = items;
        this.cookware = cookware;
        this.totalCalories = totalCalories;
        this.visibility = visibility;
        this.difficulty = difficulty;
    }
}
