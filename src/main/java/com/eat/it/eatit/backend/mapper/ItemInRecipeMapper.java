package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.ItemInRecipe;
import com.eat.it.eatit.backend.dto.ItemInRecipeDTO;

import java.util.ArrayList;
import java.util.List;

public class ItemInRecipeMapper {

    private ItemInRecipeMapper() {
    }

    public static ItemInRecipeDTO toDTO(ItemInRecipe itemInRecipe) {
        ItemInRecipeDTO itemInRecipeDTO = new ItemInRecipeDTO();
        itemInRecipeDTO.setId(itemInRecipe.getId());
        itemInRecipeDTO.setRecipeId(itemInRecipe.getRecipeId());
        itemInRecipeDTO.setItem(ItemMapper.toDTO(itemInRecipe.getItem()));
        itemInRecipeDTO.setAmount(itemInRecipe.getAmount());
        return itemInRecipeDTO;
    }
    
    public static ItemInRecipe toEntity(ItemInRecipeDTO itemInRecipeDTO) {
        ItemInRecipe itemInRecipe = new ItemInRecipe();
        itemInRecipe.setRecipeId(itemInRecipeDTO.getRecipeId());
        itemInRecipe.setItem(ItemMapper.toEntity(itemInRecipeDTO.getItem()));
        itemInRecipe.setAmount(itemInRecipeDTO.getAmount());
        return itemInRecipe;
    }

    public static List<ItemInRecipeDTO> toDTOList(List<ItemInRecipe> items) {
        if (items == null) {
            return new ArrayList<>();
        }
        return items.stream().map(ItemInRecipeMapper::toDTO).toList();
    }

    public static List<ItemInRecipe> toEntityList(List<ItemInRecipeDTO> itemDTOSet) {
        if (itemDTOSet == null) {
            return new ArrayList<>();
        }
        return itemDTOSet.stream().map(ItemInRecipeMapper::toEntity).toList();
    }
}
