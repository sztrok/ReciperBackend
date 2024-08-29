package com.eat.it.eatit.backend.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="cookware")
@NoArgsConstructor
@Getter
@Setter
public class Cookware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cookware_id")
    private long id;

    private String name;

    public Cookware(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Cookware{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
