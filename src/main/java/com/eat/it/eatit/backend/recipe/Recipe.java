package com.eat.it.eatit.backend.recipe;

import com.eat.it.eatit.backend.cookware.Cookware;
import com.eat.it.eatit.backend.item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "recipe")
@NoArgsConstructor
@Getter
@Setter
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Long ownerId;

    private String description;

    @ManyToMany
    private Set<Item> items;

    @OneToMany
    @JoinColumn(name = "cookware")
    private Set<Cookware> cookware;

    private Integer totalCalories;

    public Recipe(String name, Long ownerId, String description, Set<Item> items, Set<Cookware> cookware, Integer totalCalories) {
        this.name = name;
        this.ownerId = ownerId;
        this.description = description;
        this.items = items;
        this.cookware = cookware;
        this.totalCalories = totalCalories;
    }

    public Recipe(String name, Long ownerId, String description, Set<Item> items) {
        this.name = name;
        this.ownerId = ownerId;
        this.description = description;
        this.items = items;
    }

    public Recipe(String name, Long ownerId, String description) {
        this.name = name;
        this.ownerId = ownerId;
        this.description = description;
    }

    public Recipe(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ownerId=" + ownerId +
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
        return Objects.equals(name, recipe.name) && Objects.equals(ownerId, recipe.ownerId) && Objects.equals(description, recipe.description) && Objects.equals(items, recipe.items) && Objects.equals(cookware, recipe.cookware) && Objects.equals(totalCalories, recipe.totalCalories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, ownerId, description, items, cookware, totalCalories);
    }
}
