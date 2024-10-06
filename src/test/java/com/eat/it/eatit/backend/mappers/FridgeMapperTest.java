package com.eat.it.eatit.backend.mappers;

import com.eat.it.eatit.backend.fridge.Fridge;
import com.eat.it.eatit.backend.fridge.FridgeMapper;
import com.eat.it.eatit.backend.item.Item;
import com.eat.it.eatit.backend.fridge.FridgeDTO;
import com.eat.it.eatit.backend.item.ItemDTO;
import com.eat.it.eatit.backend.item.ItemMapper;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FridgeMapperTest {

    private Fridge fridge;
    private FridgeDTO fridgeDTO;

    @Test
    void testEntityToDTOConversion() {
        fridge = new Fridge();
        Set<Item> itemSet = Set.of(
                new Item("test item 1"),
                new Item("test item 2")
        );
        fridge.setItems(itemSet);
        fridge.setOwnerId(10L);
        fridgeDTO = FridgeMapper.toDTO(fridge);
        assertEquals(fridge.getOwnerId(), fridgeDTO.getOwnerId());
        assertEquals(ItemMapper.toDTOSet(fridge.getItems()), fridgeDTO.getItems());
    }

    @Test
    void testDTOToEntityConversion() {
        fridgeDTO = new FridgeDTO();
        Set<ItemDTO> itemDTOSet = Set.of(
                new ItemDTO("test item dto 1"),
                new ItemDTO("test item dto 2")
        );
        fridgeDTO.setItems(itemDTOSet);
        fridgeDTO.setOwnerId(20L);
        fridge = FridgeMapper.toEntity(fridgeDTO);
        assertEquals(fridgeDTO.getOwnerId(), fridge.getOwnerId());
        assertEquals(ItemMapper.toEntitySet(fridgeDTO.getItems()), fridge.getItems());
    }

    @Test
    void testConversionForNullEntityObject() {
        fridgeDTO = FridgeMapper.toDTO(fridge);
        assertNotNull(fridgeDTO);
    }

    @Test
    void testConversionForNullDTOObject() {
        fridge = FridgeMapper.toEntity(fridgeDTO);
        assertNotNull(fridge);
    }

}