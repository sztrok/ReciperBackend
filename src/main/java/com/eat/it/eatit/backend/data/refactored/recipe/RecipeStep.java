package com.eat.it.eatit.backend.data.refactored.recipe;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @OneToMany
    @JoinColumn(
            name = "recipe_step_id"
    )
    private List<RecipeIngredient> ingredients;

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = new ArrayList<>(ingredients);
    }
}
