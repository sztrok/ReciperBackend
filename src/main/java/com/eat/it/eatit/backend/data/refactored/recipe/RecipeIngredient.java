package com.eat.it.eatit.backend.data.refactored.recipe;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.enums.UnitOfMeasure;
import com.eat.it.eatit.backend.utils.ListToStringConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Item item;
    private Double quantity;
    @Convert(converter = ListToStringConverter.class)
    private List<String> qualities = new ArrayList<>();
    private UnitOfMeasure unit = UnitOfMeasure.GRAM;
    private Boolean isOptional = false;

    public void setQualities(List<String> qualities) {
        this.qualities = new ArrayList<>(qualities);
    }

}
