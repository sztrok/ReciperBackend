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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account owner;

    @OneToMany
    private List<Item> items;

    public Fridge(Account owner, List<Item> items) {
        this.owner = owner;
        this.items = items;
    }

    public Fridge(Account owner) {
        this.owner = owner;
    }
}
