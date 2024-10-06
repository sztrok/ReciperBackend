package com.eat.it.eatit.backend.item;

import com.eat.it.eatit.backend.fridge.Fridge;
import com.eat.it.eatit.backend.recipe.Recipe;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

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

    private Integer amount;

    @ManyToMany(mappedBy = "items")
    private Set<Recipe> recipesContainingItem;

    @ManyToMany(mappedBy = "items")
    private Set<Fridge> fridgesContainingItem;

    public Item(String name) {
        this.name = name;
    }

    public Item(Long id, String name, Long barcode, Integer caloriesPer100g, Integer proteinPer100G, Integer fatPer100G, Integer carbsPer100G, Integer amount) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.caloriesPer100g = caloriesPer100g;
        this.proteinPer100G = proteinPer100G;
        this.fatPer100G = fatPer100G;
        this.carbsPer100G = carbsPer100G;
        this.amount = amount;
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

    public Item(String name, Long barcode, Integer caloriesPer100g, Integer proteinPer100G, Integer fatPer100G, Integer carbsPer100G, Integer amount) {
        this.name = name;
        this.barcode = barcode;
        this.caloriesPer100g = caloriesPer100g;
        this.proteinPer100G = proteinPer100G;
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
                ", proteinPer100G=" + proteinPer100G +
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
        return Objects.equals(name, item.name) && Objects.equals(barcode, item.barcode) && Objects.equals(caloriesPer100g, item.caloriesPer100g) && Objects.equals(proteinPer100G, item.proteinPer100G) && Objects.equals(fatPer100G, item.fatPer100G) && Objects.equals(carbsPer100G, item.carbsPer100G);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, barcode, caloriesPer100g, proteinPer100G, fatPer100G, carbsPer100G);
    }
}
