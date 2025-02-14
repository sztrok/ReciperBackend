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
import com.eat.it.eatit.backend.mapper.refactored.recipe.RecipeRefactoredMapper;
import com.eat.it.eatit.backend.repository.recipe.RecipeRefactoredRepository;
import com.eat.it.eatit.backend.service.recipe.RecipeComponentService;
import com.eat.it.eatit.backend.service.recipe.RecipeIngredientService;
import com.eat.it.eatit.backend.service.recipe.RecipeRefactoredService;
import com.eat.it.eatit.backend.service.recipe.RecipeStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.eat.it.eatit.backend.mapper.refactored.recipe.RecipeRefactoredMapper.*;


@Service
public class GlobalRecipeService extends RecipeRefactoredService {

    private static final String FAST_API_GENERATOR_URL = "http://0.0.0.0:8000/recipe/from_text/single_stage";
    private static final String FAST_API_PROMPT_URL = "http://0.0.0.0:8000/recipe/from_text/single_stage";
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    protected GlobalRecipeService(RecipeRefactoredRepository repository, RecipeComponentService componentService, RecipeIngredientService ingredientService, RecipeStepService stepService) {
        super(repository, componentService, ingredientService, stepService);
    }

    public RecipeRefactoredDTO generateNewRecipeWithFastApiConnection(RecipeFastApiRequest request) {
        return getRecipeFromFastApiResponse(FAST_API_GENERATOR_URL, request);
    }

    public RecipeRefactoredDTO generateRecipeFromPrompt(RecipeFastApiRequest request) {
        return getRecipeFromFastApiResponse(FAST_API_PROMPT_URL, request);
    }

    public List<RecipeRefactoredDTO> getRecipes(Optional<List<String>> ingredients) {
        if (ingredients.isEmpty()) {
            return getAllPublicRecipes();
        }
        List<String> ingredientsList = ingredients.get();

//      Ten kod zwraca tylko przedmioty zawierajace wszystkie przedmioty z listy
        return getAllRecipesFromDatabase()
                .stream()
                .filter(recipe -> recipe.getVisibility() == Visibility.PUBLIC)
                .filter(recipe -> {
                    List<String> recipeIngredients = recipe.getIngredients().stream()
                            .map(RecipeIngredient::getItem)
                            .map(Item::getName)
                            .map(String::toLowerCase) // Ignorowanie wielkości liter
                            .toList();

                    return ingredientsList.stream()
                            .map(String::toLowerCase) // Ignorowanie wielkości liter w liście wejściowej
                            .allMatch(recipeIngredients::contains);
                })
                .map(RecipeRefactoredMapper::toDTO)
                .toList();

//        Ten kod zwraca jak przepis zawiera jakiekolwiek przedmioty z podanej listy
//        return getAllRecipesFromDatabase()
//                .stream()
//                .filter(recipe -> recipe.getVisibility() == Visibility.PUBLIC)
//                .filter(recipe -> recipe.getIngredients().stream()
//                        .map(RecipeIngredient::getItem)
//                        .map(Item::getName)
//                        .allMatch(ingredientsList::contains))
//                .map(RecipeRefactoredMapper::toDTO)
//                .toList();
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
        List<RecipeComponent> components = dto.getRecipeComponents().stream().map(componentService::save).toList();
        List<RecipeStep> steps = dto.getDetailedSteps().stream().map(stepService::save).toList();
        RecipeRefactored recipe = getRecipeRefactored(dto, steps, components);
        Set<RecipeIngredient> ingredients = new HashSet<>();
        for(RecipeComponent component : components) {
            ingredients.addAll(component.getIngredients());
        }
        recipe.getIngredients().addAll(ingredients);
        return toDTO(repository.save(recipe));
    }

    private RecipeRefactoredDTO getRecipeFromFastApiResponse(String url, RecipeFastApiRequest request) {
        HttpHeaders headers = HttpHeaders.writableHttpHeaders(new HttpHeaders());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RecipeFastApiRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<RecipeRefactoredDTO> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, RecipeRefactoredDTO.class
        );
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return addNewRecipe(response.getBody());
        }
        return new RecipeRefactoredDTO();
    }

}
