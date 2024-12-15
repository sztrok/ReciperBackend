package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.dto.ItemDTO;
import com.eat.it.eatit.backend.enums.ItemType;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        item.setCaloriesPer100g(1111.0);
        item.setCarbs(123d);
        item.setFats(11d);
        item.setProteins(324d);
        item.setItemType(ItemType.FRUIT);

        itemDTO = ItemMapper.toDTO(item);
        assertEquals(item.getName(), itemDTO.getName());
        assertEquals(item.getBarcode(), itemDTO.getBarcode());
        assertEquals(item.getCaloriesPer100g(), itemDTO.getCaloriesPer100g());
        assertEquals(item.getCarbs(), itemDTO.getCarbs());
        assertEquals(item.getFats(), itemDTO.getFats());
        assertEquals(item.getProteins(), itemDTO.getProteins());
        assertEquals(item.getItemType(), itemDTO.getItemType());
    }

    @Test
    void testDTOToEntityConversion() {
        itemDTO = new ItemDTO();
        itemDTO.setName("test dto item");
        itemDTO.setBarcode(122334356L);
        itemDTO.setCaloriesPer100g(1142311.0);
        itemDTO.setCarbs(1253d);
        itemDTO.setFats(121d);
        itemDTO.setProteins(3224d);
        itemDTO.setItemType(ItemType.LEGUME);

        item = ItemMapper.toEntity(itemDTO);
        assertEquals(item.getName(), itemDTO.getName());
        assertEquals(item.getBarcode(), itemDTO.getBarcode());
        assertEquals(item.getCaloriesPer100g(), itemDTO.getCaloriesPer100g());
        assertEquals(item.getCarbs(), itemDTO.getCarbs());
        assertEquals(item.getFats(), itemDTO.getFats());
        assertEquals(item.getProteins(), itemDTO.getProteins());
        assertEquals(item.getItemType(), itemDTO.getItemType());
    }

    @Test
    void testToDTOListConversion() {
        List<Item> itemSet = List.of(
                new Item("test item 1", 100.0, 20d, 30d, 50d),
                new Item("test item 2", 100.0, 20d, 30d, 50d),
                new Item("test item 3", 100.0, 20d, 30d, 50d)
        );
        List<ItemDTO> itemDTOExpectedSet = List.of(
                new ItemDTO("test item 1", null, 100.0, 20d, 30d, 50d),
                new ItemDTO("test item 2", null, 100.0, 20d, 30d, 50d),
                new ItemDTO("test item 3", null, 100.0, 20d, 30d, 50d)
        );
        List<ItemDTO> itemDTOSet = ItemMapper.toDTOList(itemSet);
        assertEquals(itemDTOExpectedSet, itemDTOSet);
    }

    @Test
    void testToEntityListConversion() {
        List<ItemDTO> itemDTOSet = List.of(
                new ItemDTO("test item 1", 0L, 100.0, 20d, 30d, 50d),
                new ItemDTO("test item 2", 0L, 100.0, 20d, 30d, 50d),
                new ItemDTO("test item 3", 0L, 100.0, 20d, 30d, 50d)
        );
        List<Item> itemExpectedSet = List.of(
                new Item("test item 1", 0L, 100.0, 20d, 30d, 50d),
                new Item("test item 2", 0L, 100.0, 20d, 30d, 50d),
                new Item("test item 3", 0L, 100.0, 20d, 30d, 50d)
        );
        List<Item> itemSet = ItemMapper.toEntityList(itemDTOSet);
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
        List<ItemDTO> itemDTOSet = ItemMapper.toDTOList(null);
        assertNotNull(itemDTOSet);
        assertTrue(itemDTOSet.isEmpty());
    }

    @Test
    void testConversionForDTOSet() {
        List<Item> itemSet = ItemMapper.toEntityList(null);
        assertNotNull(itemSet);
        assertTrue(itemSet.isEmpty());
    }

}