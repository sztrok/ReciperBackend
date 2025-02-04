package com.eat.it.eatit.backend.data.refactored.recipe;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.data.Recipe;
import com.eat.it.eatit.backend.enums.UnitOfMeasure;
import com.eat.it.eatit.backend.utils.ListToStringConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private Recipe recipe;
    private Item item;
    private List<Item> alternativeItems = new ArrayList<>();
    private Double quantity;
    @Convert(converter = ListToStringConverter.class)
    private List<String> qualities = new ArrayList<>();
    private UnitOfMeasure unit = UnitOfMeasure.GRAM;
    private Boolean isOptional = false;


}
