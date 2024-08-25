package com.eat.it.eatit.backend.data;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="meal_type")
@NoArgsConstructor
public class MealType {

    //TODO: create meal and item type functionality and integrate it into Recipe
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_type_id")
    private long id;
    private String type;

    public MealType(String type) {
        this.type = type;
    }
}
