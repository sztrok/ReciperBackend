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
    private List<String> tags = new ArrayList<>(); //TODO: zamienic na enum?
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "recipe_id"
    )
    private List<RecipePart> recipeParts = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private List<RecipeIngredient> ingredients = new ArrayList<>();


}
