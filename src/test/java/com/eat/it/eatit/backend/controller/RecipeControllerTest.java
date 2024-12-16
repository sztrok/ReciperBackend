package com.eat.it.eatit.backend.controller;

import com.eat.it.eatit.backend.IntegrationTest;
import com.eat.it.eatit.backend.data.Cookware;
import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.data.ItemInRecipe;
import com.eat.it.eatit.backend.data.Recipe;
import com.eat.it.eatit.backend.dto.CookwareDTO;
import com.eat.it.eatit.backend.dto.RecipeDTO;
import com.eat.it.eatit.backend.repository.CookwareRepository;
import com.eat.it.eatit.backend.repository.ItemInRecipeRepository;
import com.eat.it.eatit.backend.repository.ItemRepository;
import com.eat.it.eatit.backend.repository.RecipeRepository;
import com.eat.it.eatit.backend.service.ItemService;
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

import java.util.HashSet;
import java.util.List;

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

    @Autowired
    private ItemService itemService;

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
        recipeRepository.save(testRecipe2);

        mockMvc.perform(MockMvcRequestBuilders.get(RECIPE_API_PREFIX + "all")
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
    void shouldUpdateRecipeItems() throws Exception {
        List<Item> newItems = List.of(
                new Item("Pepper"),
                new Item("Olive oil")
        );
        itemRepository.saveAll(newItems);
        itemRepository.flush();

        List<ItemInRecipe> newRecipeItems = List.of(
                new ItemInRecipe(testRecipe.getId(), newItems.get(0), 1D),
                new ItemInRecipe(testRecipe.getId(), newItems.get(1), 2D)
        );
        itemInRecipeRepository.saveAll(newRecipeItems);
        itemInRecipeRepository.flush();

        String urlTemplate = RECIPE_API_PREFIX + "{id}/items";
        mockMvc.perform(MockMvcRequestBuilders.patch(urlTemplate, testRecipe.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRecipeItems)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].name").value("Pepper"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[1].name").value("Olive oil"));
    }

}