package com.eat.it.eatit.backend.repository.recipe;

import com.eat.it.eatit.backend.data.recipe.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {
}
