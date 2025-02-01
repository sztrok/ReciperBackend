package com.eat.it.eatit.backend.dto.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeSimpleDTO {
    private Long id;
    private String name;
    private String description;
}
