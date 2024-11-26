package com.eat.it.eatit.backend.data;

import com.eat.it.eatit.backend.enums.ItemType;
import com.eat.it.eatit.backend.listener.ItemListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@EntityListeners(ItemListener.class)
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

    private Double caloriesPer100g;

    private Double proteins;

    private Double fats;

    private Double carbs;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;


    public Item(String name) {
        this.name = name;
    }

    public Item(Long id, String name, Long barcode, Double caloriesPer100g, Double proteins, Double fats, Double carbs) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.caloriesPer100g = caloriesPer100g;
        this.proteins = proteins;
        this.fats = fats;
        this.carbs = carbs;
    }

    public Item(String name, Long barcode, Double caloriesPer100g, Double proteins, Double fats, Double carbs, ItemType itemType) {
        this.name = name;
        this.barcode = barcode;
        this.caloriesPer100g = caloriesPer100g;
        this.proteins = proteins;
        this.fats = fats;
        this.carbs = carbs;
        this.itemType = itemType;
    }

    public Item(String name, Double caloriesPer100g, Double proteins, Double fats, Double carbs) {
        this.name = name;
        this.caloriesPer100g = caloriesPer100g;
        this.proteins = proteins;
        this.fats = fats;
        this.carbs = carbs;
    }

    public Item(String name, Long barcode, Double caloriesPer100g, Double proteins, Double fats, Double carbs) {
        this.name = name;
        this.barcode = barcode;
        this.caloriesPer100g = caloriesPer100g;
        this.proteins = proteins;
        this.fats = fats;
        this.carbs = carbs;
    }


    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", barcode=" + barcode +
                ", caloriesPer100g=" + caloriesPer100g +
                ", proteins=" + proteins +
                ", fatPer100G=" + fats +
                ", carbsPer100G=" + carbs +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name) && Objects.equals(barcode, item.barcode) && Objects.equals(caloriesPer100g, item.caloriesPer100g) && Objects.equals(proteins, item.proteins) && Objects.equals(fats, item.fats) && Objects.equals(carbs, item.carbs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, barcode, caloriesPer100g, proteins, fats, carbs);
    }
}
