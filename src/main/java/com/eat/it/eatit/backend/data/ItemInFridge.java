package com.eat.it.eatit.backend.data;

import com.eat.it.eatit.backend.enums.UnitOfMeasure;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item_in_fridge")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemInFridge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
    private Double quantity;
    @Enumerated(EnumType.STRING)
    private UnitOfMeasure unit = UnitOfMeasure.GRAM;

}
