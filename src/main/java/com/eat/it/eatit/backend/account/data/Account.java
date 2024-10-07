package com.eat.it.eatit.backend.account.data;

import com.eat.it.eatit.backend.fridge.data.Fridge;
import com.eat.it.eatit.backend.recipe.data.Recipe;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="account")
@NoArgsConstructor
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    @Column(unique = true)
    private String mail;

    private String password;

    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "fridge")
    private Fridge fridge;

    @OneToMany(
            mappedBy = "ownerId"
    )
    private Set<Recipe> recipes;

    private Boolean premium;

    public Account(String username, String mail, Boolean premium) {
        this.username = username;
        this.mail = mail;
        this.premium = premium;
    }

    public Account(String username, String mail, Fridge fridge, Boolean premium) {
        this.username = username;
        this.mail = mail;
        this.fridge = fridge;
        this.premium = premium;
    }

    public Account(String username, String mail, Fridge fridge, Set<Recipe> recipes, Boolean premium) {
        this.username = username;
        this.mail = mail;
        this.fridge = fridge;
        this.recipes = recipes;
        this.premium = premium;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", mail='" + mail + '\'' +
                ", fridge=" + fridge +
                ", recipes=" + recipes +
                ", premium=" + premium +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(username, account.username) && Objects.equals(mail, account.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, mail);
    }
}
