package com.eat.it.eatit.backend.data;

import com.eat.it.eatit.backend.enums.UnitOfMeasure;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "item_in_recipe")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemInRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @PrimaryKeyJoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Long recipeId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    private Double amount = 0d;

    @Enumerated(EnumType.STRING)
    private UnitOfMeasure unit = UnitOfMeasure.GRAM;

    public ItemInRecipe(Long recipeId, Item item, Double amount) {
        this.recipeId = recipeId;
        this.item = item;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ItemInRecipe{" +
                "id=" + id +
                ", recipeId=" + recipeId +
                ", item=" + item +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemInRecipe that = (ItemInRecipe) o;
        return Objects.equals(recipeId, that.recipeId) && Objects.equals(item, that.item) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, item, amount);
    }
}
