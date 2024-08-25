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
    private List<Item> items;

    @OneToMany
    private List<Cookware> cookwares;

    private Integer total_calories;

    public Recipe(String name, Account owner, String description, List<Item> items, List<Cookware> cookwares, Integer total_calories) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.items = items;
        this.cookwares = cookwares;
        this.total_calories = total_calories;
    }

    public Recipe(String name, Account owner, String description, List<Item> items) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.items = items;
    }
}
