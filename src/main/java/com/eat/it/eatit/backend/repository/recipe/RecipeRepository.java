package com.eat.it.eatit.backend.repository.recipe;

import com.eat.it.eatit.backend.data.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
