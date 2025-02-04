package com.eat.it.eatit.backend.data.refactored.recipe;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeStep {

    @Id
    private Long id;
    private String description;
    @OneToMany
    @JoinColumn(
            name = "recipe_step_id"
    )
    private List<RecipeIngredient> ingredients;
}
