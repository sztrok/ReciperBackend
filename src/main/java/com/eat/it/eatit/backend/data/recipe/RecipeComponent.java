package com.eat.it.eatit.backend.data.recipe;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToMany
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = new ArrayList<>(ingredients);
    }
}
