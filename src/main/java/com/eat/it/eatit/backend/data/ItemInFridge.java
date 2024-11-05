package com.eat.it.eatit.backend.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "item_in_fridge")
@NoArgsConstructor
@Getter
@Setter
public class ItemInFridge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @PrimaryKeyJoinColumn(name = "fridge_id", referencedColumnName = "id")
    private Long fridgeId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    private Double amount;

    public ItemInFridge(Long fridgeId, Item item, Double amount) {
        this.fridgeId = fridgeId;
        this.item = item;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ItemInFridge{" +
                "id=" + id +
                ", fridgeId=" + fridgeId +
                ", item=" + item +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemInFridge that = (ItemInFridge) o;
        return Objects.equals(fridgeId, that.fridgeId) && Objects.equals(item, that.item) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fridgeId, item, amount);
    }
}
