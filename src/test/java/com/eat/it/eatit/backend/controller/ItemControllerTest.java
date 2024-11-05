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

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/item/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Test Item"));

        Item savedItem = itemRepository.findByNameIgnoreCase("New Test Item");
        assertNotNull(savedItem, "The new item should have been saved in the repository");
    }

    @Test
    void shouldGetItemById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/item/get/id/{id}", testItem.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Item"));
    }

//    @Test
//    void shouldUpdateItem() throws Exception {
//        testItem.setName("Updated Test Item");
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/item/{id}", testItem.getId())
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(testItem)))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Test Item"));
//
//        Item updatedItem = itemRepository.findById(testItem.getId()).orElseThrow();
//        assertEquals("Updated Test Item", updatedItem.getName(), "The item name should have been updated in the repository");
//    }

//    @Test
//    void shouldDeleteItem() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/items/{id}", testItem.getId())
//                        .contentType("application/json"))
//                .andExpect(status().isNoContent());
//
//        Item deletedItem = itemRepository.findById(testItem.getId()).orElse(null);
//        assertNull(deletedItem, "The item should have been deleted from the repository");
//    }

    @Test
    void shouldGetAllItems() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/item/get_all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test Item"));
    }

    @Test
    void shouldGetItemByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/item/get/name/{name}", "Test Item")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Item"));
    }

    @Test
    void shouldGetAllItemsByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/item/get_all/name/{name}", "Test Item")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test Item"));
    }

    @Test
    void shouldGetItemByBarcode() throws Exception {
        testItem.setBarcode(123456L);
        itemRepository.save(testItem);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/item/get/barcode/{barcode}", 123456L)
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
        mockMvc.perform(MockMvcRequestBuilders.get("/api/items/invalid-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}