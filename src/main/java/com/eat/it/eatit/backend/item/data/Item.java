package com.eat.it.eatit.backend.item.data;

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

    private Double proteins;

    private Double fatPer100G;

    private Double carbsPer100G;

    private Double amount;


    public Item(String name) {
        this.name = name;
    }

    public Item(Long id, String name, Long barcode, Integer caloriesPer100g, Double proteins, Double fatPer100G, Double carbsPer100G, Double amount) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.caloriesPer100g = caloriesPer100g;
        this.proteins = proteins;
        this.fatPer100G = fatPer100G;
        this.carbsPer100G = carbsPer100G;
        this.amount = amount;
    }

    public Item(String name, Long barcode, Integer caloriesPer100g, Double proteins, Double fatPer100G, Double carbsPer100G) {
        this.name = name;
        this.barcode = barcode;
        this.caloriesPer100g = caloriesPer100g;
        this.proteins = proteins;
        this.fatPer100G = fatPer100G;
        this.carbsPer100G = carbsPer100G;
    }

    public Item(String name, Integer caloriesPer100g, Double proteins, Double fatPer100G, Double carbsPer100G) {
        this.name = name;
        this.caloriesPer100g = caloriesPer100g;
        this.proteins = proteins;
        this.fatPer100G = fatPer100G;
        this.carbsPer100G = carbsPer100G;
    }

    public Item(String name, Long barcode, Integer caloriesPer100g, Double proteins, Double fatPer100G, Double carbsPer100G, Double amount) {
        this.name = name;
        this.barcode = barcode;
        this.caloriesPer100g = caloriesPer100g;
        this.proteins = proteins;
        this.fatPer100G = fatPer100G;
        this.carbsPer100G = carbsPer100G;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", barcode=" + barcode +
                ", caloriesPer100g=" + caloriesPer100g +
                ", proteins=" + proteins +
                ", fatPer100G=" + fatPer100G +
                ", carbsPer100G=" + carbsPer100G +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name) && Objects.equals(barcode, item.barcode) && Objects.equals(caloriesPer100g, item.caloriesPer100g) && Objects.equals(proteins, item.proteins) && Objects.equals(fatPer100G, item.fatPer100G) && Objects.equals(carbsPer100G, item.carbsPer100G);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, barcode, caloriesPer100g, proteins, fatPer100G, carbsPer100G);
    }
}
