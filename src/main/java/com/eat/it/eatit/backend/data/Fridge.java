package com.eat.it.eatit.backend.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "fridge")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Fridge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "fridge_id")
    private List<ItemInFridge> items = new ArrayList<>();

    public Fridge(List<ItemInFridge> items) {
        this.items = items;
    }

    public void setItems(List<ItemInFridge> items) {
        this.items = new ArrayList<>(items);
    }

    public void addItem(ItemInFridge item) {
        items.add(item);
    }

}
