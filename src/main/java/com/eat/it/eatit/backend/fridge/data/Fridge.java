package com.eat.it.eatit.backend.fridge.data;

import com.eat.it.eatit.backend.item.data.Item;
import com.eat.it.eatit.backend.item.data.ItemInFridge;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="fridge")
@NoArgsConstructor
@Getter
@Setter
public class Fridge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PrimaryKeyJoinColumn(name = "owner_id")
    private Long ownerId;

    @OneToMany(mappedBy = "fridge")
    private Set<ItemInFridge> items;

    public Fridge(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Fridge(Long ownerId, Set<ItemInFridge> items) {
        this.ownerId = ownerId;
        this.items = items;
    }

    public Fridge(Set<ItemInFridge> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Fridge{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", items=" + items +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fridge fridge = (Fridge) o;
        return Objects.equals(ownerId, fridge.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId);
    }
}
