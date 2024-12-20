package com.eat.it.eatit.backend.controller;

import com.eat.it.eatit.backend.IntegrationTest;
import com.eat.it.eatit.backend.data.Cookware;
import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.data.ItemInRecipe;
import com.eat.it.eatit.backend.data.Recipe;
import com.eat.it.eatit.backend.dto.CookwareDTO;
import com.eat.it.eatit.backend.dto.RecipeDTO;
import com.eat.it.eatit.backend.enums.Visibility;
import com.eat.it.eatit.backend.repository.CookwareRepository;
import com.eat.it.eatit.backend.repository.ItemInRecipeRepository;
import com.eat.it.eatit.backend.repository.ItemRepository;
import com.eat.it.eatit.backend.repository.RecipeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Transactional
@IntegrationTest
@AutoConfigureMockMvc
class RecipeControllerTest {

    private static final String RECIPE_API_PREFIX = "/api/v1/recipe/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CookwareRepository cookwareRepository;

    @Autowired
    private ItemInRecipeRepository itemInRecipeRepository;

    private Recipe testRecipe;

    @BeforeEach
    void setUp() {
        testRecipe = new Recipe();
        testRecipe.setName("Test Recipe");
        testRecipe.setDescription("This is a test recipe");
        recipeRepository.save(testRecipe);

        List<Cookware> cookwareList = List.of(
                new Cookware("Pan"),
                new Cookware("Spatula"));
        cookwareRepository.saveAll(cookwareList);

        List<Item> items = List.of(
                new Item("Salt"),
                new Item("Oil"),
                new Item("Chicken", 0D, 30D, 3D, 1D)
        );
        itemRepository.saveAll(items);

        List<ItemInRecipe> itemsInRecipe = List.of(
                new ItemInRecipe(testRecipe.getId(), items.get(0), 1D),
                new ItemInRecipe(testRecipe.getId(), items.get(1), 2D),
                new ItemInRecipe(testRecipe.getId(), items.get(2), 200D)
        );
        itemInRecipeRepository.saveAll(itemsInRecipe);

        testRecipe.setItems(itemsInRecipe);
        recipeRepository.flush();
    }

    @AfterEach
    void cleanUp() {
        recipeRepository.deleteAll();
    }

    @Test
    void shouldGetRecipeById() throws Exception {
        String urlTemplate = RECIPE_API_PREFIX + "{id}";
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate, testRecipe.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Recipe"));
    }

    @Test
    void shouldGetAllRecipes() throws Exception {
        Recipe testRecipe2 = new Recipe();
        testRecipe2.setName("Test Recipe 2");
        testRecipe2.setDescription("This is a test recipe 2");
        Recipe testRecipe3 = new Recipe();
        testRecipe3.setName("Test Recipe 3");
        testRecipe3.setDescription("This is a test recipe 3 - it's private");
        testRecipe3.setVisibility(Visibility.PRIVATE);
        recipeRepository.saveAll(List.of(testRecipe2, testRecipe3));

        mockMvc.perform(MockMvcRequestBuilders.get(RECIPE_API_PREFIX + "/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test Recipe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Test Recipe 2"));
    }

    @Test
    void shouldGetAllPublicRecipes() throws Exception {
        Recipe testRecipe2 = new Recipe();
        testRecipe2.setName("Test Recipe 2");
        testRecipe2.setDescription("This is a test recipe 2");
        Recipe testRecipe3 = new Recipe();
        testRecipe3.setName("Test Recipe 3");
        testRecipe3.setDescription("This is a test recipe 3 - it's private");
        testRecipe3.setVisibility(Visibility.PRIVATE);
        recipeRepository.saveAll(List.of(testRecipe2, testRecipe3));

        mockMvc.perform(MockMvcRequestBuilders.get(RECIPE_API_PREFIX + "/public/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test Recipe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Test Recipe 2"));
    }

    @Test
    void shouldCreateNewRecipe() throws Exception {
        Recipe newRecipe = new Recipe();
        newRecipe.setName("New Recipe");
        newRecipe.setDescription("New Recipe Description");

        String newRecipeJson = objectMapper.writeValueAsString(newRecipe);
        mockMvc.perform(MockMvcRequestBuilders.post(RECIPE_API_PREFIX + "new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newRecipeJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Recipe"));
    }

    @Test
    void shouldDeleteRecipeById() throws Exception {
        String urlTemplate = RECIPE_API_PREFIX + "{id}";
        mockMvc.perform(MockMvcRequestBuilders.delete(urlTemplate, testRecipe.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateRecipe() throws Exception {
        RecipeDTO updatedRecipe = new RecipeDTO();
        updatedRecipe.setName("Updated Recipe");
        updatedRecipe.setDescription("Updated Recipe Description");

        String urlTemplate = RECIPE_API_PREFIX + "{id}";
        mockMvc.perform(MockMvcRequestBuilders.put(urlTemplate, testRecipe.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRecipe)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Recipe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Updated Recipe Description"));
    }

    @Test
    void shouldUpdateRecipeInformation() throws Exception {
        String newName = "Updated Recipe";
        String newDescription = "New recipe description";
        String urlTemplate = RECIPE_API_PREFIX + "{id}/info";
        mockMvc.perform(MockMvcRequestBuilders.patch(urlTemplate, testRecipe.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name", newName)
                        .param("description", newDescription))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(newName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(newDescription));
    }

    @Test
    void shouldUpdateRecipeCookware() throws Exception {
        List<CookwareDTO> newCookware = List.of(
                new CookwareDTO("Garnek"),
                new CookwareDTO("Spatula")
        );

        String urlTemplate = RECIPE_API_PREFIX + "{id}/cookware";

        mockMvc.perform(MockMvcRequestBuilders.patch(urlTemplate, testRecipe.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCookware)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cookware[0].name").value("Garnek"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cookware[1].name").value("Spatula"));
    }

    @Test
    void
    shouldUpdateRecipeItems() throws Exception {
        Item newItem1 = new Item("Pepperoncino");
        Item newItem2 = new Item("Olive oil");
        itemRepository.saveAll(List.of(newItem1, newItem2));
        itemRepository.flush();

        Map<Long, Double> itemsWithAmounts = new LinkedHashMap<>();
        itemsWithAmounts.put(newItem1.getId(), 50.0);
        itemsWithAmounts.put(newItem2.getId(), 100.0);

        String urlTemplate = RECIPE_API_PREFIX + "{id}/items";
        mockMvc.perform(MockMvcRequestBuilders.patch(urlTemplate, testRecipe.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemsWithAmounts)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].item.name").value("Pepperoncino"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[1].item.name").value("Olive oil"));
    }

    @Test
    void shouldReturnBadRequest_whenRecipeWithIdDoesNotExist() throws Exception {
        String urlTemplate = RECIPE_API_PREFIX + "{id}";
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate, Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenDeletingNonExistentRecipe() throws Exception {
        String urlTemplate = RECIPE_API_PREFIX + "{id}";
        mockMvc.perform(MockMvcRequestBuilders.delete(urlTemplate, Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenUpdatingNonExistentRecipe() throws Exception {
        RecipeDTO updatedRecipe = new RecipeDTO();
        updatedRecipe.setName("Updated Recipe");
        updatedRecipe.setDescription("Updated Recipe Description");
        String urlTemplate = RECIPE_API_PREFIX + "{id}";
        mockMvc.perform(MockMvcRequestBuilders.put(urlTemplate, Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRecipe)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenUpdatingInfoForNonExistentRecipe() throws Exception {
        String newName = "updated name";
        String newDescription = "new description";
        String urlTemplate = RECIPE_API_PREFIX + "{id}/info";
        mockMvc.perform(MockMvcRequestBuilders.patch(urlTemplate, Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name", newName)
                        .param("description", newDescription))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenUpdatingCookwareForNonExistentRecipe() throws Exception {
        List<CookwareDTO> newCookware = List.of(new CookwareDTO("Szynkowar"));
        String urlTemplate = RECIPE_API_PREFIX + "{id}/cookware";
        mockMvc.perform(MockMvcRequestBuilders.patch(urlTemplate, Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCookware)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenUpdatingItemsForNonExistentRecipe() throws Exception {
        Map<Long, Double> itemsWithAmounts = Map.of(0L, 20D);
        String urlTemplate = RECIPE_API_PREFIX + "{id}/items";
        mockMvc.perform(MockMvcRequestBuilders.patch(urlTemplate, Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemsWithAmounts)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenUpdatingWithNonExistentItems() throws Exception {
        Map<Long, Double> itemsWithAmounts = Map.of(Long.MAX_VALUE, 20D);
        String urlTemplate = RECIPE_API_PREFIX + "{id}/items";
        mockMvc.perform(MockMvcRequestBuilders.patch(urlTemplate, 0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemsWithAmounts)))
                .andExpect(status().isBadRequest());
    }

}