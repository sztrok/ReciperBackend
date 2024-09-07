package com.eat.it.eatit.backend.repositories;

import com.eat.it.eatit.backend.data.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
