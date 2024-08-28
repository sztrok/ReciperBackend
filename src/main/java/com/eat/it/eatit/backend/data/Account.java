package com.eat.it.eatit.backend.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="account")
@NoArgsConstructor
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private long id;

    private String username;

    @Column(unique = true)
    private String mail;

    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "fridge")
    private Fridge fridge;

    @OneToMany(
            mappedBy = "ownerId"
    )
    private List<Recipe> recipes;

    private boolean premium;

    public Account(String username, String mail, boolean premium) {
        this.username = username;
        this.mail = mail;
        this.premium = premium;
    }

    public Account(String username, String mail, Fridge fridge, boolean premium) {
        this.username = username;
        this.mail = mail;
        this.fridge = fridge;
        this.premium = premium;
    }

    public Account(String username, String mail, Fridge fridge, List<Recipe> recipes, boolean premium) {
        this.username = username;
        this.mail = mail;
        this.fridge = fridge;
        this.recipes = recipes;
        this.premium = premium;
    }
}
