package com.eat.it.eatit.backend.service.global;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.data.recipe.RecipeComponent;
import com.eat.it.eatit.backend.data.recipe.RecipeIngredient;
import com.eat.it.eatit.backend.data.recipe.Recipe;
import com.eat.it.eatit.backend.data.recipe.RecipeStep;
import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.recipe.RecipeDTO;
import com.eat.it.eatit.backend.dto.recipe.fastapi.RecipeFastApiRequest;
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

    public RecipeDTO generateNewRecipeWithFastApiConnection(RecipeFastApiRequest request) {
        return getRecipeFromFastApiResponse(FAST_API_GENERATOR_URL, request);
    }

    public RecipeDTO generateRecipeFromPrompt(RecipeFastApiRequest request) {
        return getRecipeFromFastApiResponse(FAST_API_PROMPT_URL, request);
    }

    public List<RecipeDTO> getRecipes(
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
                    RecipeDTO recipeDTO = toDTO(recipe);
                    recipeDTO.setNumberOfAvailableIngredients(numberOfAvailableIngredients);
                    return recipeDTO;
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

    public RecipeDTO getPublicRecipeById(Authentication authentication, Long id) {
        AccountDTO account = authService.getAccountByName(authentication.getName());
        Recipe recipe = findRecipeById(id);
        if (recipe == null) {
            return null;
        }
        if (recipe.getVisibility() != Visibility.PUBLIC) {
            return null;
        }
        RecipeDTO recipeDTO = toDTO(recipe);
        Integer numOfAvailableIngredients = getNumberOfAvailableIngredients(account, recipe);
        recipeDTO.setNumberOfAvailableIngredients(numOfAvailableIngredients);
        return toDTO(recipe);
    }

    public List<RecipeDTO> getAllPublicRecipes() {
        return toDTOList(getAllRecipesFromDatabase()
                .stream()
                .filter(recipe -> recipe.getVisibility() == Visibility.PUBLIC)
                .toList());
    }

    public List<RecipeDTO> getPublicRecipesByItemTypes(Authentication authentication, List<ItemType> itemTypes) {
        AccountDTO account = authService.getAccountByName(authentication.getName());
        return getAllRecipesFromDatabase()
                .stream()
                .filter(recipe -> recipe.getVisibility() == Visibility.PUBLIC)
                .filter(recipe -> getRecipeItemTypes(recipe).containsAll(itemTypes))
                .map(recipe -> {
                    RecipeDTO recipeDTO = toDTO(recipe);
                    recipeDTO.setNumberOfAvailableIngredients(getNumberOfAvailableIngredients(account, recipe));
                    return recipeDTO;
                })
                .toList();
    }

    public List<RecipeDTO> getPublicRecipesByDifficulty(Authentication authentication, List<RecipeDifficulty> difficultyList) {
        AccountDTO account = authService.getAccountByName(authentication.getName());
        return getAllRecipesFromDatabase()
                .stream()
                .filter(recipe -> recipe.getVisibility() == Visibility.PUBLIC)
                .filter(recipe -> difficultyList.contains(recipe.getDifficulty()))
                .map(recipe -> {
                    RecipeDTO recipeDTO = toDTO(recipe);
                    recipeDTO.setNumberOfAvailableIngredients(getNumberOfAvailableIngredients(account, recipe));
                    return recipeDTO;
                })
                .toList();
    }

    public RecipeDTO addNewRecipe(RecipeDTO dto) {
        List<RecipeComponent> components = dto.getRecipeComponents().stream().map(componentService::save).toList();
        List<RecipeStep> steps = dto.getDetailedSteps().stream().map(stepService::save).toList();
        Recipe recipe = getRecipeRefactored(dto, steps, components);
        Set<RecipeIngredient> ingredients = new HashSet<>();
        for (RecipeComponent component : components) {
            ingredients.addAll(component.getIngredients());
        }
        recipe.getIngredients().addAll(ingredients);
        return toDTO(repository.save(recipe));
    }

    private RecipeDTO getRecipeFromFastApiResponse(String url, RecipeFastApiRequest request) {
        HttpHeaders headers = HttpHeaders.writableHttpHeaders(new HttpHeaders());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RecipeFastApiRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<RecipeDTO> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, RecipeDTO.class
        );
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return addNewRecipe(response.getBody());
        }
        return new RecipeDTO();
    }

    // Porownuje przepisy na podstawie ilosci kont ktore polubily przepis
    private Comparator<RecipeDTO> compareByLikes() {
        return Comparator.comparingInt(RecipeDTO::getNumberOfLikedAccounts)
                .reversed(); // Sortowanie malejąco
    }

    private Comparator<RecipeDTO> compareByAvailableIngredients() {
        return Comparator.comparingDouble((RecipeDTO dto) ->
                        (double) dto.getNumberOfAvailableIngredients() / dto.getNumberOfIngredients())
                .reversed();// Sortowanie malejąco
    }

    private Integer getNumberOfAvailableIngredients(AccountDTO account, Recipe recipe) {
        List<String> availableIngredients = account.getFridge().getItems().stream().map(item -> item.getItem().getName()).toList();
        return (int) recipe.getIngredients().stream().filter(ing -> availableIngredients.contains(ing.getItem().getName())).count();
    }

}
