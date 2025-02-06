package com.eat.it.eatit.backend.data.refactored.recipe;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.enums.UnitOfMeasure;
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
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private List<RecipeRefactored> recipes = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;
    //    private List<String> alternativeIngredients = new ArrayList<>();
    private Double quantity;
    @Convert(converter = ListToStringConverter.class)
    private List<String> qualities = new ArrayList<>();
    private UnitOfMeasure unit = UnitOfMeasure.GRAM;
    private Boolean isOptional = false;

    public void setQualities(List<String> qualities) {
        this.qualities = new ArrayList<>(qualities);
    }

    public void setRecipes(List<RecipeRefactored> recipes) {
        this.recipes = new ArrayList<>(recipes);
    }
}
