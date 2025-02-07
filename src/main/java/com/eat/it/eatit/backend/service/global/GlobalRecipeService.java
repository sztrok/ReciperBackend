package com.eat.it.eatit.backend.service.global;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.data.refactored.recipe.RecipeIngredient;
import com.eat.it.eatit.backend.data.refactored.recipe.RecipeRefactored;
import com.eat.it.eatit.backend.dto.refactored.recipe.RecipeRefactoredDTO;
import com.eat.it.eatit.backend.enums.ItemType;
import com.eat.it.eatit.backend.enums.RecipeDifficulty;
import com.eat.it.eatit.backend.enums.Visibility;
import com.eat.it.eatit.backend.repository.recipe.RecipeRefactoredRepository;
import com.eat.it.eatit.backend.service.recipe.RecipeComponentService;
import com.eat.it.eatit.backend.service.recipe.RecipeIngredientService;
import com.eat.it.eatit.backend.service.recipe.RecipeRefactoredService;
import com.eat.it.eatit.backend.service.recipe.RecipeStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.eat.it.eatit.backend.mapper.refactored.recipe.RecipeRefactoredMapper.*;


@Service
public class GlobalRecipeService extends RecipeRefactoredService {

    @Autowired
    protected GlobalRecipeService(RecipeRefactoredRepository repository, RecipeComponentService componentService, RecipeIngredientService ingredientService, RecipeStepService stepService) {
        super(repository, componentService, ingredientService, stepService);
    }

    public RecipeRefactoredDTO getPublicRecipeById(Long id) {
        RecipeRefactored recipe = findRecipeById(id);
        if (recipe == null) {
            return null;
        }
        if (recipe.getVisibility() != Visibility.PUBLIC) {
            return null;
        }
        return toDTO(recipe);
    }

    public List<RecipeRefactoredDTO> getAllPublicRecipes() {
        return toDTOList(getAllRecipesFromDatabase()
                .stream()
                .filter(recipe -> recipe.getVisibility() == Visibility.PUBLIC)
                .toList());
    }

    public List<RecipeRefactoredDTO> getPublicRecipesByItemTypes(List<ItemType> itemTypes) {
        return toDTOList(getAllRecipesFromDatabase()
                .stream()
                .filter(recipe -> recipe.getVisibility() == Visibility.PUBLIC)
                .filter(recipe -> getRecipeItemTypes(recipe).containsAll(itemTypes))
                .toList());
    }

    public List<RecipeRefactoredDTO> getPublicRecipesByDifficulty(List<RecipeDifficulty> difficultyList) {
        return toDTOList(getAllRecipesFromDatabase()
                .stream()
                .filter(recipe -> recipe.getVisibility() == Visibility.PUBLIC)
                .filter(recipe -> difficultyList.contains(recipe.getDifficulty()))
                .toList());
    }

    public void addNewRecipe(RecipeRefactoredDTO recipeDTO) {

        //TODO:
        // sprawdzić czy item istnieje, jak nie to stworzyć nowy,
        // stworzyć nowe RecipeIngredients połączone z components i steps,
        // stworzyć nowe recipe z połączonymi encjami
    }

    private RecipeRefactored findRecipeById(Long recipeId) {
        return repository.findById(recipeId).orElse(null);
    }

    private List<RecipeRefactored> getAllRecipesFromDatabase() {
        return repository.findAll();
    }

    private List<Item> getRecipeItems(RecipeRefactored recipe) {
        if (recipe == null) {
            return new ArrayList<>();
        }
        return recipe.getIngredients().stream().map(RecipeIngredient::getItem).toList();
    }

    private Set<ItemType> getRecipeItemTypes(RecipeRefactored recipe) {
        if (recipe == null) {
            return new HashSet<>();
        }
        return getRecipeItems(recipe).stream().map(Item::getItemType).collect(Collectors.toSet());
    }
}
