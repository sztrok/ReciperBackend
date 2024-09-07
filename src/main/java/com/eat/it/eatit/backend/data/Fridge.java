package com.eat.it.eatit.backend.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name="fridge")
@NoArgsConstructor
@Getter
@Setter
public class Fridge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @PrimaryKeyJoinColumn(name = "owner_id")
    private Long ownerId;

    @OneToMany
    @JoinColumn(name = "items")
    private Set<Item> items;

    public Fridge(Long ownerId, Set<Item> items) {
        this.ownerId = ownerId;
        this.items = items;
    }

    public Fridge(Set<Item> items) {
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
}
