package com.eat.it.eatit.backend.data;

import com.eat.it.eatit.backend.data.refactored.recipe.RecipeRefactored;
import com.eat.it.eatit.backend.enums.AccountRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(unique = true)
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
    private List<Recipe> accountRecipes = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "accounts_liked_recipes",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private List<Recipe> likedRecipes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ownerAccount")
    private List<RecipeRefactored> accountRecipesRefactored = new ArrayList<>();

    @ManyToMany(mappedBy = "likedAccounts")
    private List<RecipeRefactored> likedRecipesRefactored = new ArrayList<>();

    @ElementCollection(targetClass = AccountRole.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> accountRoles = new HashSet<>();

    @Setter
    private Boolean premium;

    public Account(String username, String mail, Boolean premium) {
        this.username = username;
        this.mail = mail;
        this.premium = premium;
    }

    public Account(String username, String mail, String password, Fridge fridge, List<Recipe> accountRecipes, Set<AccountRole> accountRoles, Boolean premium) {
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.fridge = fridge;
        this.accountRecipes = accountRecipes;
        this.accountRoles = accountRoles;
        this.premium = premium;
    }

    public void setAccountRecipes(List<Recipe> recipes) {
        this.accountRecipes = new ArrayList<>(recipes);
    }

    public void setAccountRoles(Set<AccountRole> accountRoles) {
        this.accountRoles = new HashSet<>(accountRoles);
    }

    public void setLikedRecipes(List<Recipe> likedRecipes) {
        this.likedRecipes = new ArrayList<>(likedRecipes);
    }

    public void addRole(AccountRole accountRole) {
        this.accountRoles.add(accountRole);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", fridge=" + fridge +
                ", recipes=" + accountRecipes +
                ", roles=" + accountRoles +
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
