package com.eat.it.eatit.backend.controller;

import com.eat.it.eatit.backend.IntegrationTest;
import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.enums.ItemType;
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

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@IntegrationTest
@AutoConfigureMockMvc
class ItemControllerTest {

    private static final String ITEM_API_PREFIX = "/api/v1/item/";

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

        String urlTemplate = "/api/v1/item";
        mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Test Item"));

        Item savedItem = itemRepository.findByNameIgnoreCase("New Test Item");
        assertNotNull(savedItem, "The new item should have been saved in the repository");
    }

    @Test
    void shouldGetItemById() throws Exception {
        String urlTemplate = ITEM_API_PREFIX + "{id}";
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate, testItem.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Item"));
    }

    @Test
    void shouldUpdateItem() throws Exception {
        testItem.setName("Updated Test Item");

        String urlTemplate = ITEM_API_PREFIX + "{id}";
        mockMvc.perform(MockMvcRequestBuilders.put(urlTemplate, testItem.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testItem)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Test Item"));

        Item updatedItem = itemRepository.findById(testItem.getId()).orElseThrow();
        assertEquals("Updated Test Item", updatedItem.getName(), "The item name should have been updated in the repository");
    }

    @Test
    void shouldDeleteItem() throws Exception {
        String urlTemplate = ITEM_API_PREFIX + "{id}";
        mockMvc.perform(MockMvcRequestBuilders.delete(urlTemplate, testItem.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk());

        Item deletedItem = itemRepository.findById(testItem.getId()).orElse(null);
        assertNull(deletedItem, "The item should have been deleted from the repository");
    }

    @Test
    void shouldGetAllItems() throws Exception {
        String urlTemplate = ITEM_API_PREFIX + "all";
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test Item"));
    }

    @Test
    void shouldGetItemByName() throws Exception {
        String urlTemplate = ITEM_API_PREFIX + "name";
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .param("name", "Test Item")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Item"));
    }

    @Test
    void shouldGetAllItemsByName() throws Exception {
        String urlTemplate = ITEM_API_PREFIX + "all/name";
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .param("name", "Test Item")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test Item"));
    }

    @Test
    void shouldGetItemByBarcode() throws Exception {
        testItem.setBarcode(123456L);
        itemRepository.save(testItem);

        String urlTemplate = ITEM_API_PREFIX + "barcode";
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .param("barcode", "123456")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.barcode").value(123456L));
    }

    @Test
    void shouldHandleNonExistentItem() throws Exception {
        String urlTemplate = "/api/items/{id}";
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate, Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldHandleInvalidItemId() throws Exception {
        String urlTemplate = "/api/items/{invalid-id}";
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate, "invalid id")
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

        String urlTemplate = ITEM_API_PREFIX + "macro_range";

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

        String urlTemplate = ITEM_API_PREFIX + "macro";

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
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 2"));
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

        String urlTemplate = ITEM_API_PREFIX + "macro_percentage";

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
    void shouldUpdateItemNutritionInformation() throws Exception {
        Long itemId = testItem.getId();
        testItem.setProteins(10D);
        testItem.setCarbs(40D);
        testItem.setFats(20D);

        Double newProteins = 11D;
        Double newCarbs = 44D;
        Double newFats = 9D;
        Double newCalories = 10000D;

        String urlTemplate = ITEM_API_PREFIX + "{id}/nutrition";

        mockMvc.perform(MockMvcRequestBuilders.patch(urlTemplate, itemId)
                        .param("proteins", String.valueOf(newProteins))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals(newProteins, testItem.getProteins());

        mockMvc.perform(MockMvcRequestBuilders.patch(urlTemplate, itemId)
                        .param("carbs", String.valueOf(newCarbs))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals(newCarbs, testItem.getCarbs());

        mockMvc.perform(MockMvcRequestBuilders.patch(urlTemplate, itemId)
                        .param("fats", String.valueOf(newFats))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals(newFats, testItem.getFats());

        mockMvc.perform(MockMvcRequestBuilders.patch(urlTemplate, itemId)
                        .param("calories", String.valueOf(newCalories))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals(newCalories, testItem.getCaloriesPer100g());

    }


    @Test
    void shouldUpdateItemGeneralInformation() throws Exception {
        Long itemId = testItem.getId();
        testItem.setBarcode(1188L);
        testItem.setItemType(ItemType.VEGETABLE);

        String newName = "changed Test Item";
        Long newBarcode = 99977778888555L;
        ItemType newItemType = ItemType.DAIRY;

        String urlTemplate = ITEM_API_PREFIX + "{id}/info";

        mockMvc.perform(MockMvcRequestBuilders.patch(urlTemplate, itemId)
                        .param("name", newName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals(newName, testItem.getName());

        mockMvc.perform(MockMvcRequestBuilders.patch(urlTemplate, itemId)
                        .param("barcode", String.valueOf(newBarcode))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals(newBarcode, testItem.getBarcode());

        mockMvc.perform(MockMvcRequestBuilders.patch(urlTemplate, itemId)
                        .param("itemType", String.valueOf(newItemType))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals(newItemType, testItem.getItemType());

    }


    @Test
    void shoudGetAllItemsForGivenTypes() throws Exception {
        Item item1 = new Item("Item 1", 165D, 10.0, 5.0, 20.0);
        Item item2 = new Item("Item 2", 118D, 15.0, 2.0, 10.0);
        Item item3 = new Item("Item 3", 312D, 8.0, 20.0, 25.0);

        item1.setItemType(ItemType.FISH);
        item2.setItemType(ItemType.GRAIN);
        item3.setItemType(ItemType.VEGETABLE);

        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);

        Set<ItemType> itemTypes1 = Set.of(ItemType.FISH, ItemType.GRAIN, ItemType.VEGETABLE);
        Set<ItemType> itemTypes2 = Set.of(ItemType.FISH, ItemType.VEGETABLE);
        Set<ItemType> itemTypes3 = Set.of(ItemType.GRAIN);
        Set<ItemType> itemTypes4 = Set.of(ItemType.POULTRY);
        Set<ItemType> itemTypes5 = Set.of(ItemType.POULTRY, ItemType.FISH);

        String urlTemplate = ITEM_API_PREFIX + "types";
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(itemTypes1)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Item 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("Item 3"));

        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(itemTypes2)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Item 3"));

        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(itemTypes3)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 2"));

        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(itemTypes4)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));

        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(itemTypes5)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 1"));
    }

    @Test
    void shouldReturnBadRequest_whenItemWithIdDoesNotExist() throws Exception {
        String urlTemplate = ITEM_API_PREFIX + "{id}";
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate, Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenGettingItemWithNoMathingName() throws Exception {
        String urlTemplate = ITEM_API_PREFIX + "name";
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .param("name", "ŹŹŹŹŹŹŹŹŹŹŹŹŹŹŹŹŹŹŹŹŹŹŹŹ TEST")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenItemWithBarcodeDoesNotExist() throws Exception {
        String urlTemplate = ITEM_API_PREFIX + "barcode";
        mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                        .param("barcode", String.valueOf(Long.MAX_VALUE))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenUpdatingNonExistentItem() throws Exception {
        testItem.setName("Updated Test Item");

        String urlTemplate = ITEM_API_PREFIX + "{id}";
        mockMvc.perform(MockMvcRequestBuilders.put(urlTemplate, Long.MAX_VALUE)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testItem)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenDeletingNonExistentItem() throws Exception {
        String urlTemplate = ITEM_API_PREFIX + "{id}";
        mockMvc.perform(MockMvcRequestBuilders.delete(urlTemplate, Long.MAX_VALUE)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldReturnBadRequest_whenUpdatingItemGeneralInformation_forNonExistentItem() throws Exception {
        String newName = "changed Test Item";
        String urlTemplate = ITEM_API_PREFIX + "{id}/info";
        mockMvc.perform(MockMvcRequestBuilders.patch(urlTemplate, Long.MAX_VALUE)
                        .contentType("application/json")
                        .param("name", newName))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenUpdatingItemNutritionInformation_forNonExistentItem() throws Exception {
        Double newFats = 11D;
        String urlTemplate = ITEM_API_PREFIX + "{id}/nutrition";
        mockMvc.perform(MockMvcRequestBuilders.patch(urlTemplate, Long.MAX_VALUE)
                        .contentType("application/json")
                        .param("fats", String.valueOf(newFats)))
                .andExpect(status().isBadRequest());
    }
}