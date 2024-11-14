package com.eat.it.eatit.backend.repository;

import com.eat.it.eatit.backend.data.ItemInRecipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemInRecipeRepository extends CrudRepository<ItemInRecipe, Long> {
}
