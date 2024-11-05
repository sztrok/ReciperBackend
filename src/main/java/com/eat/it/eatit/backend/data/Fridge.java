package com.eat.it.eatit.backend.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
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

    @OneToMany
    private Set<ItemInFridge> items = new HashSet<>();

    public Fridge(Long ownerId, Set<ItemInFridge> items) {
        this.ownerId = ownerId;
        this.items = items;
    }

    public void setItems(Set<ItemInFridge> items) {
        this.items = new HashSet<>(items);
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
