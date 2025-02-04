package com.eat.it.eatit.backend.data;

import com.eat.it.eatit.backend.enums.RecipeDifficulty;
import com.eat.it.eatit.backend.enums.Visibility;
import com.eat.it.eatit.backend.listener.RecipeListener;
import com.eat.it.eatit.backend.utils.ListToStringConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@EntityListeners(RecipeListener.class)
@Table(name = "recipe")
@NoArgsConstructor
@Getter
@Setter
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Convert(converter = ListToStringConverter.class)
    private List<String> simpleSteps;

    @Convert(converter = ListToStringConverter.class)
    private List<String> detailedSteps;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ItemInRecipe> items = new ArrayList<>();

    @ManyToMany
    private List<Cookware> cookware = new ArrayList<>();

    private Integer totalCalories;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Visibility visibility = Visibility.PUBLIC;

    @Enumerated(EnumType.STRING)
    private RecipeDifficulty difficulty = RecipeDifficulty.EASY;

    public Recipe(String name, String description, List<ItemInRecipe> items, List<Cookware> cookware, Integer totalCalories) {
        this.name = name;
        this.description = description;
        this.items = items;
        this.cookware = cookware;
        this.totalCalories = totalCalories;
    }

    public Recipe(String name, String description, List<ItemInRecipe> items, List<Cookware> cookware, Integer totalCalories, Visibility visibility) {
        this.name = name;
        this.description = description;
        this.items = items;
        this.cookware = cookware;
        this.totalCalories = totalCalories;
        this.visibility = visibility;
    }

    public Recipe(String name, String description, List<ItemInRecipe> items, List<Cookware> cookware, Integer totalCalories, Visibility visibility, RecipeDifficulty difficulty) {
        this.name = name;
        this.description = description;
        this.items = items;
        this.cookware = cookware;
        this.totalCalories = totalCalories;
        this.visibility = visibility;
        this.difficulty = difficulty;
    }

    public Recipe(String name, String description, List<String> simpleSteps, List<String> detailedSteps, List<ItemInRecipe> items, List<Cookware> cookware, Integer totalCalories, Visibility visibility, RecipeDifficulty difficulty) {
        this.name = name;
        this.description = description;
        this.simpleSteps = simpleSteps;
        this.detailedSteps = detailedSteps;
        this.items = items;
        this.cookware = cookware;
        this.totalCalories = totalCalories;
        this.visibility = visibility;
        this.difficulty = difficulty;
    }

    public Recipe(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setItems(List<ItemInRecipe> items) {
        this.items = new ArrayList<>(items);
    }

    public void setCookware(List<Cookware> cookware) {
        this.cookware = new ArrayList<>(cookware);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", items=" + items +
                ", cookware=" + cookware +
                ", totalCalories=" + totalCalories +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(name, recipe.name) && Objects.equals(description, recipe.description) && Objects.equals(items, recipe.items) && Objects.equals(cookware, recipe.cookware);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, items, cookware);
    }
}
