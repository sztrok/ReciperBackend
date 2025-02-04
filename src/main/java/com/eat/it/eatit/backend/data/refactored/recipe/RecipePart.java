package com.eat.it.eatit.backend.data.refactored.recipe;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipePart {

    @Id
    private Long id;
    private String name;
    private List<RecipeIngredient> recipeIngredients;
}
