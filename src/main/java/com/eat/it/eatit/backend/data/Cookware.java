package com.eat.it.eatit.backend.data;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="cookware")
@NoArgsConstructor
public class Cookware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cookware_id")
    private long id;

    private String name;

    public Cookware(String name) {
        this.name = name;
    }
}
