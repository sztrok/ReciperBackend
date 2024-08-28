package com.eat.it.eatit.backend.data;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name="fridge")
@NoArgsConstructor
public class Fridge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fridge_id")
    private long id;

    @OneToMany
    @JoinColumn(name = "items")
    private List<Item> items;

    public Fridge(List<Item> items) {
        this.items = items;
    }

}
