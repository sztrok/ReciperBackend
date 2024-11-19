package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.ItemInRecipe;
import com.eat.it.eatit.backend.dto.ItemInRecipeDTO;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    public static Set<ItemInRecipeDTO> toDTOSet(Set<ItemInRecipe> items) {
        if (items == null) {
            return new HashSet<>();
        }
        return items.stream().map(ItemInRecipeMapper::toDTO).collect(Collectors.toSet());
    }

    public static Set<ItemInRecipe> toEntitySet(Set<ItemInRecipeDTO> itemDTOSet) {
        if (itemDTOSet == null) {
            return new HashSet<>();
        }
        return itemDTOSet.stream().map(ItemInRecipeMapper::toEntity).collect(Collectors.toSet());
    }
}
