package com.eat.it.eatit.backend.data;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "recipe")
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Account owner;

    private String description;

    @OneToMany
    @JoinColumn(name = "items")
    private List<Item> items;

    @OneToMany
    @JoinColumn(name = "cookware")
    private List<Cookware> cookware;

    @Column(name = "total_calories")
    private Integer totalCalories;

    public Recipe(String name, Account owner, String description, List<Item> items, List<Cookware> cookware, Integer totalCalories) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.items = items;
        this.cookware = cookware;
        this.totalCalories = totalCalories;
    }

    public Recipe(String name, Account owner, String description, List<Item> items) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.items = items;
    }
}
