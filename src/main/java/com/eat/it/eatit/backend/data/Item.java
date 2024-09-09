package com.eat.it.eatit.backend.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "item")
@NoArgsConstructor
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Long barcode;

    private Integer caloriesPer100g;

    private Integer proteinPer100G;

    private Integer fatPer100G;

    private Integer carbsPer100G;

    public Item(String name) {
        this.name = name;
    }

    public Item(String name, Long barcode, Integer caloriesPer100g, Integer proteinPer100G, Integer fatPer100G, Integer carbsPer100G) {
        this.name = name;
        this.barcode = barcode;
        this.caloriesPer100g = caloriesPer100g;
        this.proteinPer100G = proteinPer100G;
        this.fatPer100G = fatPer100G;
        this.carbsPer100G = carbsPer100G;
    }

    public Item(String name, Integer caloriesPer100g, Integer proteinPer100G, Integer fatPer100G, Integer carbsPer100G) {
        this.name = name;
        this.caloriesPer100g = caloriesPer100g;
        this.proteinPer100G = proteinPer100G;
        this.fatPer100G = fatPer100G;
        this.carbsPer100G = carbsPer100G;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", barcode=" + barcode +
                ", caloriesPer100g=" + caloriesPer100g +
                ", proteinPer100G=" + proteinPer100G +
                ", fatPer100G=" + fatPer100G +
                ", carbsPer100G=" + carbsPer100G +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name) && Objects.equals(barcode, item.barcode) && Objects.equals(caloriesPer100g, item.caloriesPer100g) && Objects.equals(proteinPer100G, item.proteinPer100G) && Objects.equals(fatPer100G, item.fatPer100G) && Objects.equals(carbsPer100G, item.carbsPer100G);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, barcode, caloriesPer100g, proteinPer100G, fatPer100G, carbsPer100G);
    }
}
