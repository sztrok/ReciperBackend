package com.eat.it.eatit.backend.service.recipe;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.data.refactored.recipe.RecipeIngredient;
import com.eat.it.eatit.backend.dto.ItemDTO;
import com.eat.it.eatit.backend.dto.refactored.recipe.RecipeIngredientDTO;
import com.eat.it.eatit.backend.enums.ItemType;
import com.eat.it.eatit.backend.enums.UnitOfMeasure;
import com.eat.it.eatit.backend.repository.recipe.RecipeIngredientRepository;
import com.eat.it.eatit.backend.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeIngredientService {

    private final RecipeIngredientRepository repository;
    private final ItemService itemService;

    @Autowired
    public RecipeIngredientService(RecipeIngredientRepository repository, ItemService itemService) {
        this.repository = repository;
        this.itemService = itemService;
    }

    public RecipeIngredient save(RecipeIngredientDTO dto) {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        Item item = itemService.findItemByName(dto.getName());
        if (item == null) {
            ItemDTO newSimpleItem = new ItemDTO();
            newSimpleItem.setName(dto.getName());
            newSimpleItem.setItemType(ItemType.OTHER);
            item = itemService.createNewItem(newSimpleItem);
        }
        recipeIngredient.setItem(item);
        recipeIngredient.setQuantity(dto.getQuantity());
        recipeIngredient.setQualities(dto.getQualities());
        try {
            recipeIngredient.setUnit(UnitOfMeasure.valueOf(dto.getUnit()));
        } catch (Exception e) {
            recipeIngredient.setUnit(UnitOfMeasure.GRAM);
        }
        recipeIngredient.setIsOptional(dto.getIsOptional());

        return repository.save(recipeIngredient);
    }

}
