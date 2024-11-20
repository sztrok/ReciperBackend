package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.Fridge;
import com.eat.it.eatit.backend.data.ItemInFridge;
import com.eat.it.eatit.backend.dto.ItemInFridgeDTO;
import com.eat.it.eatit.backend.dto.FridgeDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FridgeMapperTest {

    private Fridge fridge;
    private FridgeDTO fridgeDTO;

    @Test
    void testEntityToDTOConversion() {
        fridge = new Fridge();
        List<ItemInFridge> itemList = List.of(
                new ItemInFridge(1L, null, 0d),
                new ItemInFridge(2L, null, 10d)
        );
        fridge.setItems(itemList);
        fridge.setOwnerId(10L);
        fridgeDTO = FridgeMapper.toDTO(fridge);
        assertEquals(fridge.getOwnerId(), fridgeDTO.getOwnerId());
        assertEquals(ItemInFridgeMapper.toDTOList(fridge.getItems()), fridgeDTO.getItems());
    }

    @Test
    void testDTOToEntityConversion() {
        fridgeDTO = new FridgeDTO();
        List<ItemInFridgeDTO> itemDTOList = List.of(
                new ItemInFridgeDTO(1L, null, 0d),
                new ItemInFridgeDTO(2L, null, 10d)
        );
        fridgeDTO.setItems(itemDTOList);
        fridgeDTO.setOwnerId(20L);
        fridge = FridgeMapper.toEntity(fridgeDTO);
        assertEquals(fridgeDTO.getOwnerId(), fridge.getOwnerId());
        assertEquals(ItemInFridgeMapper.toEntityList(fridgeDTO.getItems()), fridge.getItems());
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