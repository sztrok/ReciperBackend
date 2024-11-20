package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.data.ItemInFridge;
import com.eat.it.eatit.backend.dto.ItemDTO;
import com.eat.it.eatit.backend.dto.ItemInFridgeDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.eat.it.eatit.backend.mapper.ItemInFridgeMapper.toDTO;
import static com.eat.it.eatit.backend.mapper.ItemInFridgeMapper.toEntity;
import static com.eat.it.eatit.backend.mapper.ItemInFridgeMapper.toDTOList;
import static com.eat.it.eatit.backend.mapper.ItemInFridgeMapper.toEntityList;
import static org.junit.jupiter.api.Assertions.assertEquals;


class ItemInFridgeMapperTest {

    private ItemInFridge itemInFridge;
    private ItemInFridgeDTO itemInFridgeDTO;

    @Test
    void testEntityToDTOConversion() {
        itemInFridge = new ItemInFridge();
        itemInFridge.setId(1009L);
        itemInFridge.setFridgeId(101L);
        itemInFridge.setAmount(201D);

        itemInFridgeDTO = toDTO(itemInFridge);
        assertEquals(itemInFridge.getId(), itemInFridgeDTO.getId());
        assertEquals(itemInFridge.getFridgeId(), itemInFridgeDTO.getFridgeId());
        assertEquals(itemInFridge.getAmount(), itemInFridgeDTO.getAmount());
    }

    @Test
    void testDTOToEntityConversion() {
        itemInFridgeDTO = new ItemInFridgeDTO();
        itemInFridgeDTO.setFridgeId(101L);
        itemInFridgeDTO.setAmount(201D);

        itemInFridge = toEntity(itemInFridgeDTO);
        assertEquals(itemInFridgeDTO.getFridgeId(), itemInFridge.getFridgeId());
        assertEquals(itemInFridgeDTO.getAmount(), itemInFridge.getAmount());
    }

    @Test
    void testToDTOListConversion() {
        List<ItemInFridge> items = List.of(
                new ItemInFridge(101L, new Item(), 1101D),
                new ItemInFridge(102L, new Item(), 1102D),
                new ItemInFridge(103L, new Item(), 1103D)
        );
        List<ItemInFridgeDTO> expectedItems = List.of(
                new ItemInFridgeDTO(101L, new ItemDTO(), 1101D),
                new ItemInFridgeDTO(102L, new ItemDTO(), 1102D),
                new ItemInFridgeDTO(103L, new ItemDTO(), 1103D)
        );
        List<ItemInFridgeDTO> itemDTOSet = toDTOList(items);
        assertEquals(expectedItems, itemDTOSet);
    }

    @Test
    void testToEntityListConversion() {
        List<ItemInFridgeDTO> items = List.of(
                new ItemInFridgeDTO(101L, new ItemDTO(), 1101D),
                new ItemInFridgeDTO(102L, new ItemDTO(), 1102D),
                new ItemInFridgeDTO(103L, new ItemDTO(), 1103D)
        );
        List<ItemInFridge> expectedItems = List.of(
                new ItemInFridge(101L, new Item(), 1101D),
                new ItemInFridge(102L, new Item(), 1102D),
                new ItemInFridge(103L, new Item(), 1103D)
        );
        List<ItemInFridge> itemEntitySet = toEntityList(items);
        assertEquals(expectedItems, itemEntitySet);
    }

}