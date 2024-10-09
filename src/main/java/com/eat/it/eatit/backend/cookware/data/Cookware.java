package com.eat.it.eatit.backend.cookware.data;

import com.eat.it.eatit.backend.recipe.data.Recipe;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="cookware")
@NoArgsConstructor
@Getter
@Setter
public class Cookware {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "cookware")
    private Set<Recipe> recipesContainingCookware;

    public Cookware(String name) {
        this.name = name;
    }

    public Cookware(String name, Set<Recipe> recipesContainingCookware) {
        this.name = name;
        this.recipesContainingCookware = recipesContainingCookware;
    }

    @Override
    public String toString() {
        return "Cookware{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cookware cookware = (Cookware) o;
        return Objects.equals(name, cookware.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
