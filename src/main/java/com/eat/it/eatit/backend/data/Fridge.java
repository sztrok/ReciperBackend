package com.eat.it.eatit.backend.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="fridge")
@NoArgsConstructor
@Getter
@Setter
public class Fridge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fridge_id")
    private long id;

    @PrimaryKeyJoinColumn(name = "owner_id")
    private Long ownerId;

    @OneToMany
    @JoinColumn(name = "items")
    private List<Item> items;

    public Fridge(List<Item> items) {
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
