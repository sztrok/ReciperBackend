package com.eat.it.eatit.backend.item.data;

import com.eat.it.eatit.backend.fridge.data.Fridge;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item_in_fridge")
@NoArgsConstructor
@Getter
@Setter
public class ItemInFridge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Fridge fridge;

    @OneToOne(fetch = FetchType.LAZY)
    private Item item;

    private Double amount;

}
