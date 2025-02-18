package com.eat.it.eatit.backend.service.global;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.data.refactored.recipe.RecipeComponent;
import com.eat.it.eatit.backend.data.refactored.recipe.RecipeIngredient;
import com.eat.it.eatit.backend.data.refactored.recipe.RecipeRefactored;
import com.eat.it.eatit.backend.data.refactored.recipe.RecipeStep;
import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.refactored.recipe.RecipeRefactoredDTO;
import com.eat.it.eatit.backend.dto.refactored.recipe.fastapi.RecipeFastApiRequest;
import com.eat.it.eatit.backend.enums.ItemType;
import com.eat.it.eatit.backend.enums.RecipeDifficulty;
import com.eat.it.eatit.backend.enums.SortingParameter;
import com.eat.it.eatit.backend.enums.Visibility;
import com.eat.it.eatit.backend.repository.recipe.RecipeRepository;
import com.eat.it.eatit.backend.service.AccountAuthAndAccessService;
import com.eat.it.eatit.backend.service.recipe.RecipeComponentService;
import com.eat.it.eatit.backend.service.recipe.RecipeIngredientService;
import com.eat.it.eatit.backend.service.recipe.RecipeService;
import com.eat.it.eatit.backend.service.recipe.RecipeStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.eat.it.eatit.backend.mapper.refactored.recipe.RecipeRefactoredMapper.*;


@Service
public class GlobalRecipeService extends RecipeService {

    private static final String FAST_API_GENERATOR_URL = "http://fastapi-backend:8000/recipe/from_text/single_stage";
    private static final String FAST_API_PROMPT_URL = "http://fastapi-backend:8000/recipe/from_prompt";
    private final RestTemplate restTemplate = new RestTemplate();
    private final AccountAuthAndAccessService authService;

    @Autowired
    protected GlobalRecipeService(
            RecipeRepository repository,
            RecipeComponentService componentService,
            RecipeIngredientService ingredientService,
            RecipeStepService stepService,
            AccountAuthAndAccessService authService
    ) {
        super(repository, componentService, ingredientService, stepService);
        this.authService = authService;
    }

    public RecipeRefactoredDTO generateNewRecipeWithFastApiConnection(RecipeFastApiRequest request) {
        return getRecipeFromFastApiResponse(FAST_API_GENERATOR_URL, request);
    }

    public RecipeRefactoredDTO generateRecipeFromPrompt(RecipeFastApiRequest request) {
        return getRecipeFromFastApiResponse(FAST_API_PROMPT_URL, request);
    }

    public List<RecipeRefactoredDTO> getRecipes(
            Authentication authentication,
            Optional<List<String>> ingredients,
            SortingParameter sortingParameter
    ) {
        AccountDTO account = authService.getAccountByName(authentication.getName());
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
                .map(recipe -> {
                    Integer numberOfAvailableIngredients = getNumberOfAvailableIngredients(account, recipe);
                    RecipeRefactoredDTO recipeRefactoredDTO = toDTO(recipe);
                    recipeRefactoredDTO.setNumberOfAvailableIngredients(numberOfAvailableIngredients);
                    return recipeRefactoredDTO;
                })
                .sorted(sortingParameter == SortingParameter.NUM_OF_LIKES ? compareByLikes() : compareByAvailableIngredients())//sortuje po ilosci kont ktore polubily przepis LUB po ilosci posiadanych skladnikow
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

    public RecipeRefactoredDTO getPublicRecipeById(Authentication authentication, Long id) {
        AccountDTO account = authService.getAccountByName(authentication.getName());
        RecipeRefactored recipe = findRecipeById(id);
        if (recipe == null) {
            return null;
        }
        if (recipe.getVisibility() != Visibility.PUBLIC) {
            return null;
        }
        RecipeRefactoredDTO recipeRefactoredDTO = toDTO(recipe);
        Integer numOfAvailableIngredients = getNumberOfAvailableIngredients(account, recipe);
        recipeRefactoredDTO.setNumberOfAvailableIngredients(numOfAvailableIngredients);
        return toDTO(recipe);
    }

    public List<RecipeRefactoredDTO> getAllPublicRecipes() {
        return toDTOList(getAllRecipesFromDatabase()
                .stream()
                .filter(recipe -> recipe.getVisibility() == Visibility.PUBLIC)
                .toList());
    }

    public List<RecipeRefactoredDTO> getPublicRecipesByItemTypes(Authentication authentication, List<ItemType> itemTypes) {
        AccountDTO account = authService.getAccountByName(authentication.getName());
        return getAllRecipesFromDatabase()
                .stream()
                .filter(recipe -> recipe.getVisibility() == Visibility.PUBLIC)
                .filter(recipe -> getRecipeItemTypes(recipe).containsAll(itemTypes))
                .map(recipe -> {
                    RecipeRefactoredDTO recipeRefactoredDTO = toDTO(recipe);
                    recipeRefactoredDTO.setNumberOfAvailableIngredients(getNumberOfAvailableIngredients(account, recipe));
                    return recipeRefactoredDTO;
                })
                .toList();
    }

    public List<RecipeRefactoredDTO> getPublicRecipesByDifficulty(Authentication authentication, List<RecipeDifficulty> difficultyList) {
        AccountDTO account = authService.getAccountByName(authentication.getName());
        return getAllRecipesFromDatabase()
                .stream()
                .filter(recipe -> recipe.getVisibility() == Visibility.PUBLIC)
                .filter(recipe -> difficultyList.contains(recipe.getDifficulty()))
                .map(recipe -> {
                    RecipeRefactoredDTO recipeRefactoredDTO = toDTO(recipe);
                    recipeRefactoredDTO.setNumberOfAvailableIngredients(getNumberOfAvailableIngredients(account, recipe));
                    return recipeRefactoredDTO;
                })
                .toList();
    }

    public RecipeRefactoredDTO addNewRecipe(RecipeRefactoredDTO dto) {
        List<RecipeComponent> components = dto.getRecipeComponents().stream().map(componentService::save).toList();
        List<RecipeStep> steps = dto.getDetailedSteps().stream().map(stepService::save).toList();
        RecipeRefactored recipe = getRecipeRefactored(dto, steps, components);
        Set<RecipeIngredient> ingredients = new HashSet<>();
        for (RecipeComponent component : components) {
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

    // Porownuje przepisy na podstawie ilosci kont ktore polubily przepis
    private Comparator<RecipeRefactoredDTO> compareByLikes() {
        return Comparator.comparingInt(RecipeRefactoredDTO::getNumberOfLikedAccounts)
                .reversed(); // Sortowanie malejąco
    }

    private Comparator<RecipeRefactoredDTO> compareByAvailableIngredients() {
        return Comparator.comparingDouble((RecipeRefactoredDTO dto) ->
                        (double) dto.getNumberOfAvailableIngredients() / dto.getNumberOfIngredients())
                .reversed();// Sortowanie malejąco
    }

    private Integer getNumberOfAvailableIngredients(AccountDTO account, RecipeRefactored recipe) {
        List<String> availableIngredients = account.getFridge().getItems().stream().map(item -> item.getItem().getName()).toList();
        return (int) recipe.getIngredients().stream().filter(ing -> availableIngredients.contains(ing.getItem().getName())).count();
    }

}
