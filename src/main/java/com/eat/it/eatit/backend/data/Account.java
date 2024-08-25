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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private long id;

    private String username;

    private String mail;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fridge_id")
    private Fridge fridge;

    @OneToMany
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
