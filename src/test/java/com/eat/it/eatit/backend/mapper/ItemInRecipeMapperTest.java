package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.dto.ItemDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.eat.it.eatit.backend.mapper.ItemInRecipeMapper.*;
import static com.eat.it.eatit.backend.mapper.ItemInRecipeMapper.toEntityList;
import static org.junit.jupiter.api.Assertions.*;

class ItemInRecipeMapperTest {

    private ItemInRecipe itemInRecipe;
    private ItemInRecipeDTO itemInRecipeDTO;

    @Test
    void testEntityToDTOConversion() {
        itemInRecipe = new ItemInRecipe();
        itemInRecipe.setId(1009L);
        itemInRecipe.setRecipeId(101L);
        itemInRecipe.setAmount(201D);

        itemInRecipeDTO = toDTO(itemInRecipe);
        assertEquals(itemInRecipe.getId(), itemInRecipeDTO.getId());
        assertEquals(itemInRecipe.getRecipeId(), itemInRecipeDTO.getRecipeId());
        assertEquals(itemInRecipe.getAmount(), itemInRecipeDTO.getAmount());
    }

    @Test
    void testDTOToEntityConversion() {
        itemInRecipeDTO = new ItemInRecipeDTO();
        itemInRecipeDTO.setRecipeId(101L);
        itemInRecipeDTO.setAmount(201D);

        itemInRecipe = toEntity(itemInRecipeDTO);
        assertEquals(itemInRecipeDTO.getRecipeId(), itemInRecipe.getRecipeId());
        assertEquals(itemInRecipeDTO.getAmount(), itemInRecipe.getAmount());
    }

    @Test
    void testToDTOListConversion() {
        List<ItemInRecipe> items = List.of(
                new ItemInRecipe(101L, new Item(), 1101D),
                new ItemInRecipe(102L, new Item(), 1102D),
                new ItemInRecipe(103L, new Item(), 1103D)
        );
        List<ItemInRecipeDTO> expectedItems = List.of(
                new ItemInRecipeDTO(101L, new ItemDTO(), 1101D),
                new ItemInRecipeDTO(102L, new ItemDTO(), 1102D),
                new ItemInRecipeDTO(103L, new ItemDTO(), 1103D)
        );
        List<ItemInRecipeDTO> itemDTOSet = toDTOList(items);
        assertEquals(expectedItems, itemDTOSet);
    }

    @Test
    void testToEntityListConversion() {
        List<ItemInRecipeDTO> items = List.of(
                new ItemInRecipeDTO(101L, new ItemDTO(), 1101D),
                new ItemInRecipeDTO(102L, new ItemDTO(), 1102D),
                new ItemInRecipeDTO(103L, new ItemDTO(), 1103D)
        );
        List<ItemInRecipe> expectedItems = List.of(
                new ItemInRecipe(101L, new Item(), 1101D),
                new ItemInRecipe(102L, new Item(), 1102D),
                new ItemInRecipe(103L, new Item(), 1103D)
        );
        List<ItemInRecipe> itemEntitySet = toEntityList(items);
        assertEquals(expectedItems, itemEntitySet);
    }

}