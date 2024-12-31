package com.eat.it.eatit.backend.service;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.dto.ItemDTO;
import com.eat.it.eatit.backend.enums.ItemType;
import com.eat.it.eatit.backend.enums.Macros;
import com.eat.it.eatit.backend.mapper.ItemMapper;
import com.eat.it.eatit.backend.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemRepository itemRepository;

    @Test
    void testGetItemById() {
        Item item = new Item();
        item.setId(1L);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        ItemDTO itemDTO = itemService.getItemById(1L);

        assertNotNull(itemDTO);
    }

    @Test
    void testGetItemByName() {
        Item item = new Item();
        item.setName("Test");
        when(itemRepository.findByNameIgnoreCase("Test")).thenReturn(item);

        ItemDTO itemDTO = itemService.getItemByName("Test");

        assertNotNull(itemDTO);
        assertEquals("Test", itemDTO.getName());
    }

    @Test
    void testAddNewItem() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName("New Item");
        Item item = ItemMapper.toEntity(itemDTO);
        when(itemRepository.save(item)).thenReturn(item);

        ItemDTO savedItemDTO = itemService.addNewItem(itemDTO);

        assertNotNull(savedItemDTO);
        assertEquals("New Item", savedItemDTO.getName());
    }

    @Test
    void testGetAllItems() {
        Item item = new Item();
        item.setName("Item1");
        when(itemRepository.findAll()).thenReturn(Collections.singletonList(item));

        assertNotNull(itemService.getAllItems());
    }

    @Test
    void testGetItemsByTypes() {
        Item item = new Item();
        item.setItemType(ItemType.DAIRY);
        when(itemRepository.findAllByItemTypeIn(Collections.singleton(ItemType.DAIRY))).thenReturn(List.of(item));

        assertNotNull(itemService.getItemsByTypes(Collections.singleton(ItemType.DAIRY)));
    }

    @Test
    void testGetItemsFilteredByMacrosInRange() {
        Item item = new Item();
        item.setCaloriesPer100g(50.0);
        when(itemRepository.findAllByCaloriesPer100gBetween(0.0, 100.0)).thenReturn(List.of(item));

        assertNotNull(itemService.getItemsFilteredByMacrosInRange(0.0, 100.0, Macros.CALORIES));
    }

    @Test
    void testDeleteItemById() {
        Item item = new Item();
        item.setId(1L);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        assertTrue(itemService.deleteItemById(1L));

        verify(itemRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetItemByBarcode() {
        Item item = new Item();
        item.setBarcode(1234567890L);
        when(itemRepository.findByBarcode(1234567890L)).thenReturn(item);

        ItemDTO itemDTO = itemService.getItemByBarcode(1234567890L);

        assertNotNull(itemDTO);
        assertEquals(1234567890L, itemDTO.getBarcode());
    }

    // Test methods for updateItem are assumed to be included
}