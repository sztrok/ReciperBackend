package com.eat.it.eatit.backend.mappers;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.dto.ItemDTO;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemMapperTest {

    private Item item;
    private ItemDTO itemDTO;

    @Test
    void testEntityToDTOConversion() {
        item = new Item();
        item.setName("test item");
        item.setBarcode(12283456L);
        item.setCaloriesPer100g(1111);
        item.setCarbsPer100G(123);
        item.setFatPer100G(11);
        item.setProteinPer100G(324);

        itemDTO = ItemMapper.toDTO(item);
        assertEquals(item.getName(), itemDTO.getName());
        assertEquals(item.getBarcode(), itemDTO.getBarcode());
        assertEquals(item.getCaloriesPer100g(), itemDTO.getCaloriesPer100g());
        assertEquals(item.getCarbsPer100G(), itemDTO.getCarbsPer100G());
        assertEquals(item.getFatPer100G(), itemDTO.getFatPer100G());
        assertEquals(item.getProteinPer100G(), itemDTO.getProteinPer100G());
    }

    @Test
    void testDTOToEntityConversion() {
        itemDTO = new ItemDTO();
        itemDTO.setName("test dto item");
        itemDTO.setBarcode(122334356L);
        itemDTO.setCaloriesPer100g(1142311);
        itemDTO.setCarbsPer100G(1253);
        itemDTO.setFatPer100G(121);
        itemDTO.setProteinPer100G(3224);

        item = ItemMapper.toEntity(itemDTO);
        assertEquals(item.getName(), itemDTO.getName());
        assertEquals(item.getBarcode(), itemDTO.getBarcode());
        assertEquals(item.getCaloriesPer100g(), itemDTO.getCaloriesPer100g());
        assertEquals(item.getCarbsPer100G(), itemDTO.getCarbsPer100G());
        assertEquals(item.getFatPer100G(), itemDTO.getFatPer100G());
        assertEquals(item.getProteinPer100G(), itemDTO.getProteinPer100G());
    }

    @Test
    void testToDTOSetConversion() {
        Set<Item> itemSet = Set.of(
                new Item("test item 1", 100, 20, 30, 50),
                new Item("test item 2", 100, 20, 30, 50),
                new Item("test item 3", 100, 20, 30, 50)
        );
        Set<ItemDTO> itemDTOExpectedSet = Set.of(
                new ItemDTO("test item 1", null, 100, 20, 30, 50),
                new ItemDTO("test item 2", null, 100, 20, 30, 50),
                new ItemDTO("test item 3", null, 100, 20, 30, 50)
        );
        Set<ItemDTO> itemDTOSet = ItemMapper.toDTOSet(itemSet);
        assertEquals(itemDTOExpectedSet, itemDTOSet);
    }

    @Test
    void testToEntitySetConversion() {
        Set<ItemDTO> itemDTOSet = Set.of(
                new ItemDTO("test item 1", 0L, 100, 20, 30, 50),
                new ItemDTO("test item 2", 0L, 100, 20, 30, 50),
                new ItemDTO("test item 3", 0L, 100, 20, 30, 50)
        );
        Set<Item> itemExpectedSet = Set.of(
                new Item("test item 1", 0L, 100, 20, 30, 50),
                new Item("test item 2", 0L, 100, 20, 30, 50),
                new Item("test item 3", 0L, 100, 20, 30, 50)
        );
        Set<Item> itemSet = ItemMapper.toEntitySet(itemDTOSet);
        assertEquals(itemExpectedSet, itemSet);
    }

    @Test
    void testConversionForNullEntityObject() {
        itemDTO = ItemMapper.toDTO(item);
        assertNotNull(itemDTO);
    }

    @Test
    void testConversionForNullDTOObject() {
        item = ItemMapper.toEntity(itemDTO);
        assertNotNull(item);
    }

    @Test
    void testConversionForNullEntitySet() {
        Set<ItemDTO> itemDTOSet = ItemMapper.toDTOSet(null);
        assertNotNull(itemDTOSet);
        assertTrue(itemDTOSet.isEmpty());
    }

    @Test
    void testConversionForDTOSet() {
        Set<Item> itemSet = ItemMapper.toEntitySet(null);
        assertNotNull(itemSet);
        assertTrue(itemSet.isEmpty());
    }

}