package com.eat.it.eatit.backend.account.data;

import com.eat.it.eatit.backend.fridge.data.Fridge;
import com.eat.it.eatit.backend.recipe.data.Recipe;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="account")
@NoArgsConstructor
@Getter
//@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String username;

    @Setter
    @Column(unique = true)
    private String mail;

    @Setter
    private String password;

    @Setter
    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "fridge")
    private Fridge fridge;

    @OneToMany
    @JoinColumn(name = "owner_id")
    private Set<Recipe> recipes = new HashSet<>();

    @ElementCollection(targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @Setter
    private Boolean premium;

    public Account(String username, String mail, Boolean premium) {
        this.username = username;
        this.mail = mail;
        this.premium = premium;
    }

    public Account(String username, String mail, Fridge fridge, Set<Recipe> recipes, Boolean premium) {
        this.username = username;
        this.mail = mail;
        this.fridge = fridge;
        this.recipes = recipes;
        this.premium = premium;
    }

    public Account(String username, String mail, String password, Fridge fridge, Set<Recipe> recipes, Boolean premium) {
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.fridge = fridge;
        this.recipes = recipes;
        this.premium = premium;
    }


    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = new HashSet<>(recipes);
    }

    public void setRoles(Set<Role> roles) {
        this.roles = new HashSet<>(roles);
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", fridge=" + fridge +
                ", recipes=" + recipes +
                ", roles=" + roles +
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
