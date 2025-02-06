package com.eat.it.eatit.backend.repository.recipe;

import com.eat.it.eatit.backend.data.refactored.recipe.RecipeRefactored;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRefactoredRepository extends JpaRepository<RecipeRefactored, Long> {
}
