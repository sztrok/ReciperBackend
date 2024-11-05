package com.eat.it.eatit.backend.mappers;

import com.eat.it.eatit.backend.data.Fridge;
import com.eat.it.eatit.backend.data.ItemInFridge;
import com.eat.it.eatit.backend.dto.ItemInFridgeDTO;
import com.eat.it.eatit.backend.mapper.FridgeMapper;
import com.eat.it.eatit.backend.dto.FridgeDTO;
import com.eat.it.eatit.backend.mapper.ItemInFridgeMapper;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FridgeMapperTest {

    private Fridge fridge;
    private FridgeDTO fridgeDTO;

    @Test
    void testEntityToDTOConversion() {
        fridge = new Fridge();
        Set<ItemInFridge> itemSet = Set.of(
                new ItemInFridge(1L, null, 0d),
                new ItemInFridge(2L, null, 10d)
        );
        fridge.setItems(itemSet);
        fridge.setOwnerId(10L);
        fridgeDTO = FridgeMapper.toDTO(fridge);
        assertEquals(fridge.getOwnerId(), fridgeDTO.getOwnerId());
        assertEquals(ItemInFridgeMapper.toDTOSet(fridge.getItems()), fridgeDTO.getItems());
    }

    @Test
    void testDTOToEntityConversion() {
        fridgeDTO = new FridgeDTO();
        Set<ItemInFridgeDTO> itemDTOSet = Set.of(
                new ItemInFridgeDTO(1L, null, 0d),
                new ItemInFridgeDTO(2L, null, 10d)
        );
        fridgeDTO.setItems(itemDTOSet);
        fridgeDTO.setOwnerId(20L);
        fridge = FridgeMapper.toEntity(fridgeDTO);
        assertEquals(fridgeDTO.getOwnerId(), fridge.getOwnerId());
        assertEquals(ItemInFridgeMapper.toEntitySet(fridgeDTO.getItems()), fridge.getItems());
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