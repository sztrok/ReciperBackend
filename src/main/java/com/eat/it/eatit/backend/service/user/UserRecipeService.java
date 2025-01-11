package com.eat.it.eatit.backend.service.user;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.data.ItemInRecipe;
import com.eat.it.eatit.backend.data.Recipe;
import com.eat.it.eatit.backend.dto.RecipeDTO;
import com.eat.it.eatit.backend.enums.ItemType;
import com.eat.it.eatit.backend.enums.RecipeDifficulty;
import com.eat.it.eatit.backend.enums.Visibility;
import com.eat.it.eatit.backend.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.eat.it.eatit.backend.mapper.RecipeMapper.*;
import static com.eat.it.eatit.backend.mapper.RecipeMapper.toDTO;

@Service
public class UserRecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public UserRecipeService(
            RecipeRepository recipeRepository
    ) {
        this.recipeRepository = recipeRepository;
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

    public List<RecipeDTO> getAllPublicRecipes() {
        return toDTOList(getAllRecipesFromDatabase()
                .stream()
                .filter(recipe -> recipe.getVisibility() == Visibility.PUBLIC)
                .toList());
    }

    public List<RecipeDTO> getPublicRecipesByItemTypes(List<ItemType> itemTypes) {
        return toDTOList(getAllRecipesFromDatabase()
                .stream()
                .filter(recipe -> recipe.getVisibility() == Visibility.PUBLIC)
                .filter(recipe -> getRecipeItemTypes(recipe).containsAll(itemTypes))
                .toList());
    }

    public List<RecipeDTO> getPublicRecipesByDifficulty(List<RecipeDifficulty> difficultyList) {
        return toDTOList(getAllRecipesFromDatabase()
                .stream()
                .filter(recipe -> recipe.getVisibility() == Visibility.PUBLIC)
                .filter(recipe -> difficultyList.contains(recipe.getDifficulty()))
                .toList());
    }

    private Recipe findRecipeById(Long recipeId) {
        return recipeRepository.findById(recipeId).orElse(null);
    }

    private List<Recipe> getAllRecipesFromDatabase() {
        return recipeRepository.findAll();
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
