package com.eat.it.eatit.backend.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "recipe")
@NoArgsConstructor
@Getter
@Setter
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private Long ownerId;

    private String description;

    @OneToMany
    @JoinColumn(name = "items")
    private List<Item> items;

    @OneToMany
    @JoinColumn(name = "cookware")
    private List<Cookware> cookware;

    private Integer totalCalories;

    public Recipe(String name, Long ownerId, String description, List<Item> items, List<Cookware> cookware, Integer totalCalories) {
        this.name = name;
        this.ownerId = ownerId;
        this.description = description;
        this.items = items;
        this.cookware = cookware;
        this.totalCalories = totalCalories;
    }

    public Recipe(String name, Long ownerId, String description, List<Item> items) {
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
}
