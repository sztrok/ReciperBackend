package com.eat.it.eatit.backend.service;

import com.eat.it.eatit.backend.data.ItemInRecipe;
import com.eat.it.eatit.backend.repository.ItemInRecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemInRecipeService {

    private final ItemInRecipeRepository itemInRecipeRepository;

    @Autowired
    public ItemInRecipeService(ItemInRecipeRepository itemInRecipeRepository) {
        this.itemInRecipeRepository = itemInRecipeRepository;
    }

    protected void saveItemsInRecipe(List<ItemInRecipe> items) {
        itemInRecipeRepository.saveAll(items);
    }
}
