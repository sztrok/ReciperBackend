package com.eat.it.eatit.backend.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item")
@NoArgsConstructor
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private long id;

    private String name;

    private long barcode;

    @Column(name = "calories_per_100G")
    private int caloriesPer100g;

    @Column(name = "protein_per_100G")
    private int proteinPer100G;

    @Column(name = "fat_per_100G")
    private int fatPer100G;

    @Column(name = "carbs_per_100G")
    private int carbsPer100G;


    public Item(String name, long barcode, int caloriesPer100g, int proteinPer100G, int fatPer100G, int carbsPer100G) {
        this.name = name;
        this.barcode = barcode;
        this.caloriesPer100g = caloriesPer100g;
        this.proteinPer100G = proteinPer100G;
        this.fatPer100G = fatPer100G;
        this.carbsPer100G = carbsPer100G;
    }

    public Item(String name, int caloriesPer100g, int proteinPer100G, int fatPer100G, int carbsPer100G) {
        this.name = name;
        this.caloriesPer100g = caloriesPer100g;
        this.proteinPer100G = proteinPer100G;
        this.fatPer100G = fatPer100G;
        this.carbsPer100G = carbsPer100G;
    }
}
