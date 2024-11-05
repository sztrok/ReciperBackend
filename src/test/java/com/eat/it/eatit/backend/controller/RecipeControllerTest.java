package com.eat.it.eatit.backend.controller;

import com.eat.it.eatit.backend.IntegrationTest;
import com.eat.it.eatit.backend.data.Cookware;
import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.data.Recipe;
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

import java.util.HashSet;
import java.util.List;

@SpringBootTest
@Transactional
@IntegrationTest
@AutoConfigureMockMvc

class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RecipeRepository recipeRepository;

    private Recipe testRecipe;
    
    @BeforeEach
    void init() {
        testRecipe = new Recipe(
                "Test Recipe",
                "This is a test description",
                new HashSet<>(List.of(new Item("Egg", 100.0, 12.0, 10.0, 1.0))),
                new HashSet<>(List.of(new Cookware("Pan"))),
                500
        );
        recipeRepository.save(testRecipe);
    }
    
    @AfterEach
    void cleanUp() {
        recipeRepository.deleteAll();
    }

    @Test
    void testGetRecipeById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recipe/get/id/{id}", testRecipe.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testRecipe.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(testRecipe.getDescription()));
    }

    @Test
    void testGetAllRecipes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recipe/get_all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value(testRecipe.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].description").value(testRecipe.getDescription()));
    }

    @Test
    void testCreateRecipe() throws Exception {
        Recipe newRecipe = new Recipe(
                "New Recipe",
                "This is a new description",
                new HashSet<>(List.of(new Item("Milk", 50.0, 3.0, 2.0, 0.5))),
                new HashSet<>(List.of(new Cookware("Spoon"))),
                200
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/recipe/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRecipe)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(newRecipe.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(newRecipe.getDescription()));
    }

//    @Test
//    void testUpdateRecipe() throws Exception {
//        testRecipe.setName("Updated Recipe");
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/recipes/{id}", testRecipe.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(testRecipe)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Recipe"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(testRecipe.getDescription()));
//    }
//
//    @Test
//    void testDeleteRecipe() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/recipes/{id}", testRecipe.getId()))
//                .andExpect(MockMvcResultMatchers.status().isNoContent());
//    }
}