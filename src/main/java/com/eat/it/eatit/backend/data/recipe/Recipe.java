package com.eat.it.eatit.backend.data.recipe;

import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.enums.RecipeDifficulty;
import com.eat.it.eatit.backend.enums.Visibility;
import com.eat.it.eatit.backend.utils.ListToStringConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(length = 3000)
    private String description;

    @ManyToOne
    private Account ownerAccount;

    @ManyToMany
    private List<Account> likedAccounts = new ArrayList<>();

    @ManyToMany
    private List<Account> savedAccounts = new ArrayList<>();

    @Convert(converter = ListToStringConverter.class)
    @Column(length = 3000)
    private List<String> simpleSteps = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "recipe_id"
    )
    private List<RecipeStep> detailedSteps = new ArrayList<>();

    @Convert(converter = ListToStringConverter.class)
    private List<String> tips = new ArrayList<>();

    private String imageUrl = null;

    @Convert(converter = ListToStringConverter.class)
    private List<String> tags = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "recipe_id"
    )
    private List<RecipeComponent> recipeComponents = new ArrayList<>();

    @OneToMany
    @JoinColumn(
            name = "recipe_id"
    )
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Visibility visibility = Visibility.PUBLIC;

    @Enumerated(EnumType.STRING)
    private RecipeDifficulty difficulty = RecipeDifficulty.EASY;


    public void setSimpleSteps(List<String> simpleSteps) {
        this.simpleSteps = new ArrayList<>(simpleSteps);
    }

    public void setDetailedSteps(List<RecipeStep> detailedSteps) {
        this.detailedSteps = new ArrayList<>(detailedSteps);
    }

    public void setTips(List<String> tips) {
        this.tips = new ArrayList<>(tips);
    }

    public void setTags(List<String> tags) {
        this.tags = new ArrayList<>(tags);
    }

    public void setRecipeComponents(List<RecipeComponent> recipeComponents) {
        this.recipeComponents = new ArrayList<>(recipeComponents);
    }

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = new ArrayList<>(ingredients);
    }

    public void setLikedAccounts(List<Account> likedAccounts) {
        this.likedAccounts = new ArrayList<>(likedAccounts);
    }

    public void setSavedAccounts(List<Account> savedAccounts) {
        this.savedAccounts = new ArrayList<>(savedAccounts);
    }

    public void addLikedAccount(Account account) {
        this.likedAccounts.add(account);
    }

    public void removeLikedAccount(Account account) {
        this.likedAccounts = this.likedAccounts.stream().filter(acc -> !acc.getId().equals(account.getId())).toList();
    }

    public void addSavedAccount(Account account) {
        this.savedAccounts.add(account);
    }

    public void removeSavedAccount(Account account) {
        this.savedAccounts = this.savedAccounts.stream().filter(acc -> !acc.getId().equals(account.getId())).toList();
    }
}
