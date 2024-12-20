package com.eat.it.eatit.backend.service;

import com.eat.it.eatit.backend.data.Cookware;
import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.data.ItemInRecipe;
import com.eat.it.eatit.backend.data.Recipe;
import com.eat.it.eatit.backend.dto.CookwareDTO;
import com.eat.it.eatit.backend.dto.RecipeDTO;
import com.eat.it.eatit.backend.enums.ItemType;
import com.eat.it.eatit.backend.enums.RecipeDifficulty;
import com.eat.it.eatit.backend.enums.Visibility;
import com.eat.it.eatit.backend.mapper.CookwareMapper;
import com.eat.it.eatit.backend.mapper.ItemInRecipeMapper;
import com.eat.it.eatit.backend.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.eat.it.eatit.backend.mapper.RecipeMapper.*;
import static com.eat.it.eatit.backend.utils.UtilsKt.updateField;

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
    CookwareService cookwareService;

    @Autowired
    public RecipeService(
            RecipeRepository recipeRepository,
            ItemService itemService,
            ItemInRecipeService itemInRecipeService,
            CookwareService cookwareService) {
        this.recipeRepository = recipeRepository;
        this.itemService = itemService;
        this.itemInRecipeService = itemInRecipeService;
        this.cookwareService = cookwareService;
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

    public List<RecipeDTO> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        List<RecipeDTO> recipeDTOList = new ArrayList<>();
        recipes.forEach(recipe -> recipeDTOList.add(toDTO(recipe)));
        return recipeDTOList;
    }

    public List<RecipeDTO> getAllPublicRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        List<RecipeDTO> recipeDTOList = new ArrayList<>();
        recipes.stream().filter(recipe -> recipe.getVisibility() == Visibility.PUBLIC).forEach(recipe -> recipeDTOList.add(toDTO(recipe)));
        return recipeDTOList;
    }

    public List<RecipeDTO> getRecipesForAccount(Long accountId, List<Visibility> visibilityList) {
        return null;
    }

    public List<RecipeDTO> getRecipesByItemTypes(List<ItemType> itemTypes) {
        return null;
    }

    public List<RecipeDTO> getRecipesByDifficulty(List<RecipeDifficulty> difficultyList) {
        return null;
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
    public boolean deleteRecipeById(Long id) {
        Recipe recipe = findRecipeById(id);
        if (recipe == null) {
            return false;
        }
        recipeRepository.delete(recipe);
        return true;
    }

    @Transactional
    public RecipeDTO updateRecipeById(Long id, RecipeDTO recipeDTO) {
        Recipe recipe = findRecipeById(id);
        if (recipe == null) {
            return null;
        }
        updateField(recipeDTO.getName(), recipe::setName);
        updateField(recipeDTO.getDescription(), recipe::setDescription);
        updateField(recipeDTO.getCookware(), r -> recipe.setCookware(CookwareMapper.toEntityList(r)));
        updateField(recipeDTO.getItems(), r -> recipe.setItems(ItemInRecipeMapper.toEntityList(r)));
        Recipe savedRecipe = recipeRepository.save(recipe);
        return toDTO(savedRecipe);
    }

    @Transactional
    public RecipeDTO updateDescription(Long id, String name, String description) {
        Recipe recipe = findRecipeById(id);
        if (recipe == null) {
            return null;
        }
        updateField(name, recipe::setName);
        updateField(description, recipe::setDescription);
        Recipe savedRecipe = recipeRepository.save(recipe);
        return toDTO(savedRecipe);
    }

    @Transactional
    public RecipeDTO updateCookware(Long id, List<CookwareDTO> cookwareDTOList) {
        Recipe recipe = findRecipeById(id);
        if (recipe == null) {
            return null;
        }
        List<Cookware> newCookware = new ArrayList<>();
        for (CookwareDTO cookwareDTO : cookwareDTOList) {
            Cookware cookware = cookwareService.getCookwareByName(cookwareDTO.getName());
            if (cookware == null) {
                newCookware.add(cookwareService.createNewCookware(cookwareDTO));
            } else {
                newCookware.add(cookware);
            }
        }
        updateField(newCookware, recipe::setCookware);
        Recipe savedRecipe = recipeRepository.save(recipe);
        return toDTO(savedRecipe);
    }

    @Transactional
    public RecipeDTO updateItems(Long id, Map<Long, Double> items) {
        Recipe recipe = findRecipeById(id);
        if (recipe == null) {
            return null;
        }

        List<ItemInRecipe> currentItems = recipe.getItems();
        currentItems.removeIf(itemInRecipe -> !items.containsKey(itemInRecipe.getItem().getId()));

        for (Map.Entry<Long, Double> entry : items.entrySet()) {
            Long itemId = entry.getKey();
            Double amount = entry.getValue();

            ItemInRecipe existingItem = currentItems.stream()
                    .filter(itemInRecipe -> itemInRecipe.getItem().getId().equals(itemId))
                    .findFirst()
                    .orElse(null);

            if (existingItem != null) {
                existingItem.setAmount(amount);
            } else {
                Item item = itemService.findItemById(itemId);
                if (item == null) {
                    return null;
                }
                currentItems.add(new ItemInRecipe(id, item, amount));
            }
        }
        Recipe savedRecipe = recipeRepository.save(recipe);

        return toDTO(savedRecipe);
    }

    private Recipe findRecipeById(Long recipeId) {
        return recipeRepository.findById(recipeId).orElse(null);
    }
}
