package com.eat.it.eatit.backend.service;

import com.eat.it.eatit.backend.data.*;
import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.CookwareDTO;
import com.eat.it.eatit.backend.dto.RecipeDTO;
import com.eat.it.eatit.backend.enums.ItemType;
import com.eat.it.eatit.backend.enums.RecipeDifficulty;
import com.eat.it.eatit.backend.enums.Visibility;
import com.eat.it.eatit.backend.mapper.RecipeMapper;
import com.eat.it.eatit.backend.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.eat.it.eatit.backend.mapper.RecipeMapper.*;
import static com.eat.it.eatit.backend.utils.UtilsKt.updateField;
import static com.eat.it.eatit.backend.utils.recipe.UpdateRecipeFields.updateRecipeFields;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class that provides operations related to recipes.
 * This class interacts with the RecipeRepository to perform CRUD operations
 * and handles mapping between Recipe and RecipeDTO objects.
 */
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final AccountAuthAndAccessService accountService;
    private final ItemService itemService;
    private final ItemInRecipeService itemInRecipeService;
    private final CookwareService cookwareService;

    @Autowired
    public RecipeService(
            RecipeRepository recipeRepository,
            ItemService itemService,
            ItemInRecipeService itemInRecipeService,
            CookwareService cookwareService,
            AccountAuthAndAccessService accountService
    ) {
        this.recipeRepository = recipeRepository;
        this.itemService = itemService;
        this.itemInRecipeService = itemInRecipeService;
        this.cookwareService = cookwareService;
        this.accountService = accountService;
    }

    /**
     * Retrieves a recipe based on its unique identifier.
     *
     * @param id the unique identifier of the recipe to be retrieved
     * @return a ResponseEntity containing the RecipeDTO object if the recipe is found,
     * or a ResponseEntity with a not found status if the recipe does not exist
     */
    public RecipeDTO getRecipeById(Long id) {
        Recipe recipe = findRecipeById(id);
        if (recipe == null) {
            return null;
        }
        return toDTO(recipe);
    }

    public RecipeDTO getPublicRecipeById(Long id) {
        Recipe recipe = findRecipeById(id);
        if (recipe == null) {
            return null;
        }
        if (recipe.getVisibility() != Visibility.PUBLIC) {
            return null;
        }
        return toDTO(recipe);
    }

//    public List<RecipeDTO> getAllRecipesForUser(String username) {
//        Long accountId = accountService.getAccountByName(username).getId();
//        return getAllRecipesFromDatabase().stream().filter(recipe -> recipe.get)
//    }

    public List<RecipeDTO> getAllRecipes() {
        return toDTOList(getAllRecipesFromDatabase());
    }

    public List<RecipeDTO> getAllPublicRecipes() {
        return toDTOList(getAllRecipesFromDatabase()
                .stream()
                .filter(recipe -> recipe.getVisibility() == Visibility.PUBLIC)
                .toList());
    }

    public List<RecipeDTO> getRecipesForAccount(Long accountId, List<Visibility> visibilityList) {
        return toDTOList(getAccountRecipes(accountId)
                .stream()
                .filter(recipe -> visibilityList.contains(recipe.getVisibility()))
                .toList());
    }

    public List<RecipeDTO> getAllRecipesByItemTypes(List<ItemType> itemTypes) {
        return toDTOList(getAllRecipesFromDatabase()
                .stream()
                .filter(recipe -> getRecipeItemTypes(recipe).containsAll(itemTypes))
                .toList());
    }

    public List<RecipeDTO> getPublicRecipesByItemTypes(List<ItemType> itemTypes) {
        return toDTOList(getAllRecipesFromDatabase()
                .stream()
                .filter(recipe -> recipe.getVisibility() == Visibility.PUBLIC)
                .filter(recipe -> getRecipeItemTypes(recipe).containsAll(itemTypes))
                .toList());
    }

    public List<RecipeDTO> getAllRecipesByDifficulty(List<RecipeDifficulty> difficultyList) {
        return toDTOList(getAllRecipesFromDatabase()
                .stream()
                .filter(recipe -> difficultyList.contains(recipe.getDifficulty()))
                .toList());
    }

    public List<RecipeDTO> getPublicRecipesByDifficulty(List<RecipeDifficulty> difficultyList) {
        return toDTOList(getAllRecipesFromDatabase()
                .stream()
                .filter(recipe -> recipe.getVisibility() == Visibility.PUBLIC)
                .filter(recipe -> difficultyList.contains(recipe.getDifficulty()))
                .toList());
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
        recipe = saveRecipe(recipe);
        return toDTO(recipe);
    }

    @Transactional
    public void addNewRecipes(List<Recipe> recipeList) {
        saveRecipes(recipeList);
    }

    @Transactional
    public boolean deleteRecipeById(Long id) {
        Recipe recipe = findRecipeById(id);
        if (recipe == null) {
            return false;
        }
        deleteRecipe(recipe);
        return true;
    }

    @Transactional
    public RecipeDTO updateRecipeById(Long id, RecipeDTO recipeDTO) {
        Recipe recipe = findRecipeById(id);
        return updateRecipeFields(recipeDTO, recipe);
    }

    @Transactional
    public RecipeDTO updateDescription(Long id, String name, String description) {
        Recipe recipe = findRecipeById(id);
        if (recipe == null) {
            return null;
        }
        updateField(name, recipe::setName);
        updateField(description, recipe::setDescription);
        return toDTO(recipe);
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
        return toDTO(recipe);
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
        return toDTO(recipe);
    }

    private Recipe findRecipeById(Long recipeId) {
        return recipeRepository.findById(recipeId).orElse(null);
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    private void saveRecipes(List<Recipe> recipes) {
        recipeRepository.saveAll(recipes);
    }

    private void deleteRecipe(Recipe recipe) {
        recipeRepository.delete(recipe);
    }

    private List<Recipe> getAllRecipesFromDatabase() {
        return recipeRepository.findAll();
    }

    private List<Recipe> getAccountRecipes(Long accountId) {
        AccountDTO account = accountService.getAccountById(accountId);
        if (account == null) {
            return new ArrayList<>();
        }
        return account.getAccountRecipes().stream().map(RecipeMapper::toEntity).toList();
    }

    private List<Item> getRecipeItems(Recipe recipe) {
        if (recipe == null) {
            return new ArrayList<>();
        }
        return recipe.getItems().stream().map(ItemInRecipe::getItem).toList();
    }

    private Set<ItemType> getRecipeItemTypes(Recipe recipe) {
        if (recipe == null) {
            return new HashSet<>();
        }
        return getRecipeItems(recipe).stream().map(Item::getItemType).collect(Collectors.toSet());
    }

}
