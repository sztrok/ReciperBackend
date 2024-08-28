package com.eat.it.eatit.backend.data;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name="account")
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private long id;

    private String username;

    @Column(unique = true)
    private String mail;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fridge")
    private Fridge fridge;

    @OneToMany
    @JoinColumn(name = "recipes")
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
