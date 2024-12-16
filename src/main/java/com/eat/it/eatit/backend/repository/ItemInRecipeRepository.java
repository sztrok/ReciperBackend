package com.eat.it.eatit.backend.repository;

import com.eat.it.eatit.backend.data.ItemInRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemInRecipeRepository extends JpaRepository<ItemInRecipe, Long> {
}
