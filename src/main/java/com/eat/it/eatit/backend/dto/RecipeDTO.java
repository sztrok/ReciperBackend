package com.eat.it.eatit.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipeDTO {

    private String name;
    private Long ownerId;
    private String description;
    private List<ItemDTO> items;
    private List<CookwareDTO> cookware;
    private Integer totalCalories;

    public RecipeDTO(String name, Long ownerId, String description, List<ItemDTO> items, List<CookwareDTO> cookware, Integer totalCalories) {
        this.name = name;
        this.ownerId = ownerId;
        this.description = description;
        this.items = items;
        this.cookware = cookware;
        this.totalCalories = totalCalories;
    }
}
