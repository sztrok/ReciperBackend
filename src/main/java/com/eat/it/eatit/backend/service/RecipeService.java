package com.eat.it.eatit.backend.service;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.data.ItemInRecipe;
import com.eat.it.eatit.backend.data.Recipe;
import com.eat.it.eatit.backend.dto.RecipeDTO;
import com.eat.it.eatit.backend.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.eat.it.eatit.backend.mapper.RecipeMapper.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service class that provides operations related to recipes.
 * This class interacts with the RecipeRepository to perform CRUD operations
 * and handles mapping between Recipe and RecipeDTO objects.
 */
@Service
public class RecipeService {

    RecipeRepository recipeRepository;
    ItemService itemService;
    ItemInRecipeService itemInRecipeService;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, ItemService itemService, ItemInRecipeService itemInRecipeService) {
        this.recipeRepository = recipeRepository;
        this.itemService = itemService;
        this.itemInRecipeService = itemInRecipeService;
    }

    /**
     * Retrieves a recipe based on its unique identifier.
     *
     * @param id the unique identifier of the recipe to be retrieved
     * @return a ResponseEntity containing the RecipeDTO object if the recipe is found,
     * or a ResponseEntity with a not found status if the recipe does not exist
     */
    public RecipeDTO getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        if (recipe == null) {
            return null;
        }
        return toDTO(recipe);
    }

    /**
     * Retrieves all recipes from the database and maps them to RecipeDTO objects.
     *
     * @return A ResponseEntity containing a list of RecipeDTO objects representing all recipes.
     */
    public List<RecipeDTO> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        List<RecipeDTO> recipeDTOList = new ArrayList<>();
        for (Recipe recipe : recipes) {
            recipeDTOList.add(toDTO(recipe));
        }
        return recipeDTOList;
    }

    /**
     * Adds a new recipe to the repository.
     *
     * @param recipeDTO the RecipeDTO object containing the details of the new recipe to be added
     * @return a ResponseEntity containing the added RecipeDTO object
     */
    @Transactional
    public RecipeDTO addNewRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = toEntity(recipeDTO);
        recipe = recipeRepository.save(recipe);
        return toDTO(recipe);
    }

    @Transactional
    public RecipeDTO addItemsToRecipe(Long recipeId, Map<Long, Double> itemsWithAmounts) {
        Recipe recipe = findRecipeById(recipeId);
        if(recipe == null) {
            return null;
        }
        List<ItemInRecipe> addedItems = new ArrayList<>();
        for(Long id : itemsWithAmounts.keySet()) {
            Item item = itemService.findItemById(id);
            if(item == null) {
                return null;
            }
            ItemInRecipe newItem = new ItemInRecipe(recipeId, item, itemsWithAmounts.get(id));
            addedItems.add(newItem);
        }
        itemInRecipeService.saveItemsInRecipe(addedItems);
        Recipe addedRecipe = recipeRepository.save(recipe);
        return toDTO(addedRecipe);
    }

    private Recipe findRecipeById(Long recipeId) {
        return recipeRepository.findById(recipeId).orElse(null);
    }
}
