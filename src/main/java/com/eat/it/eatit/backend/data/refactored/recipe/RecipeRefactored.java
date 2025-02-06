package com.eat.it.eatit.backend.data.refactored.recipe;

import com.eat.it.eatit.backend.utils.ListToStringConverter;
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
public class RecipeRefactored {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Convert(converter = ListToStringConverter.class)
    private List<String> simpleSteps = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "recipe_id"
    )
    private List<RecipeStep> detailedSteps = new ArrayList<>();

    @Convert(converter = ListToStringConverter.class)
    private List<String> tips = new ArrayList<>();

    private String imageUrl = "";

    @Convert(converter = ListToStringConverter.class)
    private List<String> tags = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "recipe_id"
    )
    private List<RecipeComponent> recipeComponents = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "recipes")
    private List<RecipeIngredient> ingredients = new ArrayList<>();


    public void setSimpleSteps(List<String> simpleSteps) {
        this.simpleSteps = new ArrayList<>(simpleSteps);
    }

    public void setDetailedSteps(List<RecipeStep> detailedSteps) {
        this.detailedSteps = new ArrayList<>(detailedSteps);
    }

    public void setTips(List<String> tips) {
        this.tips = new ArrayList<>(tips);
    }

    public void setTags(List<String> tags) {
        this.tags = new ArrayList<>(tags);
    }

    public void setRecipeComponents(List<RecipeComponent> recipeComponents) {
        this.recipeComponents = new ArrayList<>(recipeComponents);
    }

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = new ArrayList<>(ingredients);
    }
}
