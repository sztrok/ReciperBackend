package com.eat.it.eatit.backend.data;

import com.eat.it.eatit.backend.data.recipe.Recipe;
import com.eat.it.eatit.backend.enums.AccountRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.Data;

import java.util.*;

@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String mail;

    private String password;

    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "fridge_id")
    private Fridge fridge;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ownerAccount")
    private List<Recipe> accountRecipes = new ArrayList<>();

    @ManyToMany(mappedBy = "likedAccounts")
    private List<Recipe> likedRecipes = new ArrayList<>();

    @ElementCollection(targetClass = AccountRole.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> accountRoles = new HashSet<>();

    private Boolean premium;

    private Boolean isExpired = false;

    private Boolean isLocked = false;

    public Account(String username, String mail, Boolean premium) {
        this.username = username;
        this.mail = mail;
        this.premium = premium;
    }

    public void setAccountRoles(Set<AccountRole> accountRoles) {
        this.accountRoles = new HashSet<>(accountRoles);
    }

    public void addRole(AccountRole accountRole) {
        this.accountRoles.add(accountRole);
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
