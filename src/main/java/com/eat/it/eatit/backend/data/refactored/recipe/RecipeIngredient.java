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
    private Long id;
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private RecipeRefactored recipe;
    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;
//    private List<Item> alternativeItems = new ArrayList<>();
    private Double quantity;
    @Convert(converter = ListToStringConverter.class)
    private List<String> qualities = new ArrayList<>();
    private UnitOfMeasure unit = UnitOfMeasure.GRAM;
    private Boolean isOptional = false;


}
