package com.eat.it.eatit.backend.service.global;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.data.refactored.recipe.RecipeComponent;
import com.eat.it.eatit.backend.data.refactored.recipe.RecipeIngredient;
import com.eat.it.eatit.backend.data.refactored.recipe.RecipeRefactored;
import com.eat.it.eatit.backend.data.refactored.recipe.RecipeStep;
import com.eat.it.eatit.backend.dto.refactored.recipe.RecipeRefactoredDTO;
import com.eat.it.eatit.backend.dto.refactored.recipe.fastapi.RecipeFastApiRequest;
import com.eat.it.eatit.backend.enums.ItemType;
import com.eat.it.eatit.backend.enums.RecipeDifficulty;
import com.eat.it.eatit.backend.enums.Visibility;
import com.eat.it.eatit.backend.repository.recipe.RecipeRefactoredRepository;
import com.eat.it.eatit.backend.service.recipe.RecipeComponentService;
import com.eat.it.eatit.backend.service.recipe.RecipeIngredientService;
import com.eat.it.eatit.backend.service.recipe.RecipeRefactoredService;
import com.eat.it.eatit.backend.service.recipe.RecipeStepService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static com.eat.it.eatit.backend.mapper.refactored.recipe.RecipeRefactoredMapper.*;


@Service
public class GlobalRecipeService extends RecipeRefactoredService {

    private static final String FAST_API_URL = "http://0.0.0.0:8000/recipe/from_text/single_stage";
    private final RestTemplate restTemplate = new RestTemplate();
    private final RecipeComponentService recipeComponentService;

    @Autowired
    protected GlobalRecipeService(RecipeRefactoredRepository repository, RecipeComponentService componentService, RecipeIngredientService ingredientService, RecipeStepService stepService, RecipeComponentService recipeComponentService) {
        super(repository, componentService, ingredientService, stepService);
        this.recipeComponentService = recipeComponentService;
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

    public RecipeRefactoredDTO addNewRecipe(RecipeRefactoredDTO dto) {
        List<RecipeComponent> components = dto.getRecipeComponents().stream().map(recipeComponentService::save).toList();
        List<RecipeStep> steps = dto.getDetailedSteps().stream().map(stepService::save).toList();
        RecipeRefactored recipe = getRecipeRefactored(dto, steps, components);
        return toDTO(repository.save(recipe));
    }

    public RecipeRefactoredDTO generateNewRecipeWithFastApiConnection(RecipeFastApiRequest request) {
        HttpHeaders headers = HttpHeaders.writableHttpHeaders(new HttpHeaders());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RecipeFastApiRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<RecipeRefactoredDTO> response = restTemplate.exchange(
                FAST_API_URL, HttpMethod.POST, entity, RecipeRefactoredDTO.class
        );
        if(response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return addNewRecipe(response.getBody());
        }
        return new RecipeRefactoredDTO();
    }

    @NotNull
    private static RecipeRefactored getRecipeRefactored(RecipeRefactoredDTO dto, List<RecipeStep> steps, List<RecipeComponent> components) {
        RecipeRefactored recipe = new RecipeRefactored();
        recipe.setName(dto.getName());
        recipe.setDescription(dto.getDescription());
        recipe.setSimpleSteps(dto.getSimpleSteps());
        recipe.setDetailedSteps(steps);
        recipe.setTips(dto.getTips());
        recipe.setImageUrl(dto.getImageUrl());
        recipe.setTags(dto.getTags());
        recipe.setRecipeComponents(components);
        recipe.setVisibility(dto.getVisibility());
        recipe.setDifficulty(dto.getDifficulty());
        return recipe;
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
