package com.eat.it.eatit.backend.controller;

import com.eat.it.eatit.backend.IntegrationTest;
import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.repository.ItemRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@IntegrationTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ItemRepository itemRepository;

    private Item testItem;

    @BeforeEach
    void init() {
        itemRepository.deleteAll();
        testItem = new Item();
        testItem.setName("Test Item");
        itemRepository.save(testItem);
    }

    @AfterEach
    void cleanUp() {
        itemRepository.deleteAll();
    }

    @Test
    void shouldCreateNewItem() throws Exception {
        Item newItem = new Item();
        newItem.setName("New Test Item");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/item/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Test Item"));

        Item savedItem = itemRepository.findByNameIgnoreCase("New Test Item");
        assertNotNull(savedItem, "The new item should have been saved in the repository");
    }

    @Test
    void shouldGetItemById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/item/{id}", testItem.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Item"));
    }

    @Test
    void shouldUpdateItem() throws Exception {
        testItem.setName("Updated Test Item");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/item/{id}", testItem.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testItem)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Test Item"));

        Item updatedItem = itemRepository.findById(testItem.getId()).orElseThrow();
        assertEquals("Updated Test Item", updatedItem.getName(), "The item name should have been updated in the repository");
    }

    @Test
    void shouldDeleteItem() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/item/{id}", testItem.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk());

        Item deletedItem = itemRepository.findById(testItem.getId()).orElse(null);
        assertNull(deletedItem, "The item should have been deleted from the repository");
    }

    @Test
    void shouldGetAllItems() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/item/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test Item"));
    }

    @Test
    void shouldGetItemByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/item/name")
                        .param("name", "Test Item")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Item"));
    }

    @Test
    void shouldGetAllItemsByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/item/all/name")
                        .param("name", "Test Item")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test Item"));
    }

    @Test
    void shouldGetItemByBarcode() throws Exception {
        testItem.setBarcode(123456L);
        itemRepository.save(testItem);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/item/barcode")
                        .param("barcode", "123456")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.barcode").value(123456L));
    }

    @Test
    void shouldHandleNonExistentItem() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/items/{id}", Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldHandleInvalidItemId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/items/{invalid-id}", "invalid id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void shouldGetItemsFilteredByMacrosInRange() throws Exception {
        // Create and save test items
        Item item1 = new Item("Item 1", 100D, 10.0, 5.0, 20.0);
        Item item2 = new Item("Item 2", 200D, 15.0, 2.0, 10.0);
        Item item3 = new Item("Item 3", 300D, 8.0, 6.0, 25.0);
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);

        String urlTemplate = "/api/v1/item/macro_range";

        // Filter by proteins between 8 and 12
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .param("minValue", "8.0")
                        .param("maxValue", "12.0")
                        .param("macros", "PROTEINS")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Item 3"));

        // Filter by fats between 2 and 5
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .param("minValue", "2.0")
                        .param("maxValue", "5.0")
                        .param("macros", "FATS")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Item 2"));

        // Filter by carbs between 10 and 25
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .param("minValue", "10.0")
                        .param("maxValue", "25.0")
                        .param("macros", "CARBS")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Item 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("Item 3"));

        // Filter by calories between 100 and 300
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .param("minValue", "100.0")
                        .param("maxValue", "300.0")
                        .param("macros", "CALORIES")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Item 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("Item 3"));
    }

    @Test
    void shouldFilterItemsByMacrosAndCalories() throws Exception {
        // Create and save test items
        Item item1 = new Item("Item 1", 100D, 10.0, 5.0, 20.0);
        Item item2 = new Item("Item 2", 200D, 15.0, 2.0, 10.0);
        itemRepository.save(item1);
        itemRepository.save(item2);

        String urlTemplate = "/api/v1/item/macro";

        // Filter by proteins greater than or equal to 10
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .param("value", "10.0")
                        .param("macros", "PROTEINS")
                        .param("comparator", "GREATER_THAN_OR_EQUAL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Item 2"));

        // Filter by proteins less than or equal to 10
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .param("value", "10.0")
                        .param("macros", "PROTEINS")
                        .param("comparator", "LESS_THAN_OR_EQUAL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 1"));

        // Filter by FATS greater than or equal to 5
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .param("value", "5.0")
                        .param("macros", "FATS")
                        .param("comparator", "GREATER_THAN_OR_EQUAL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 1"));

        // Filter by FATS less than or equal to 5
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .param("value", "5.0")
                        .param("macros", "FATS")
                        .param("comparator", "LESS_THAN_OR_EQUAL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Item 2"));

        // Filter by carbs greater than or equal to 20
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .param("value", "20.0")
                        .param("macros", "CARBS")
                        .param("comparator", "GREATER_THAN_OR_EQUAL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 1"));

        // Filter by carbs less than or equal to 20
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .param("value", "20.0")
                        .param("macros", "CARBS")
                        .param("comparator", "LESS_THAN_OR_EQUAL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Item 2"));

        // Filter by calories greater than or equal to 100
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .param("value", "100")
                        .param("macros", "CALORIES")
                        .param("comparator", "GREATER_THAN_OR_EQUAL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Item 2"));

        // Filter by calories less than or equal to 150
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .param("value", "150")
                        .param("macros", "CALORIES")
                        .param("comparator", "LESS_THAN_OR_EQUAL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 1"));
    }

    @Test
    void shouldGetItemsFilteredByMacrosPercentage() throws Exception {
        // Create and save test items
        Item item1 = new Item("Item 1", 165D, 10.0, 5.0, 20.0);
        Item item2 = new Item("Item 2", 118D, 15.0, 2.0, 10.0);
        Item item3 = new Item("Item 3", 312D, 8.0, 20.0, 25.0);
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);

        String urlTemplate = "/api/v1/item/macro_percentage";

        // Filter by proteins percentage between 0 and 25
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .param("minPercentage", "0.0")
                        .param("maxPercentage", "25.0")
                        .param("macros", "PROTEINS")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Item 3"));

        // Filter by fats percentage between 25 and 60
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .param("minPercentage", "25.0")
                        .param("maxPercentage", "60.0")
                        .param("macros", "FATS")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Item 3"));

        // Filter by carbs percentage between 30 and 45
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .param("minPercentage", "30.0")
                        .param("maxPercentage", "45.0")
                        .param("macros", "CARBS")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Item 3"));

    }

    @Test
    void shouldReturnBadRequest_whenItemWithIdDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/item/{id}", Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenGettingItemWithNoMathingName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/item/name")
                        .param("name", "ŹŹŹŹŹŹŹŹŹŹŹŹŹŹŹŹŹŹŹŹŹŹŹŹ TEST")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenItemWithBarcodeDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/item/barcode")
                        .param("barcode", String.valueOf(Long.MAX_VALUE))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenUpdatingNonExistentItem() throws Exception {
        testItem.setName("Updated Test Item");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/item/{id}", Long.MAX_VALUE)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testItem)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenDeletingNonExistentItem() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/item/{id}", Long.MAX_VALUE)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    //TODO: dodać testy żeby pokrywały cały controller
}