package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.ItemInRecipe;
import com.eat.it.eatit.backend.dto.ItemInRecipeDTO;

import java.util.HashSet;
import java.util.Set;

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
        Set<ItemInRecipeDTO> itemInRecipeDTOSet = new HashSet<>();
        for (ItemInRecipe item : items) {
            itemInRecipeDTOSet.add(
                    new ItemInRecipeDTO(
                            item.getId(),
                            item.getRecipeId(),
                            ItemMapper.toDTO(item.getItem()),
                            item.getAmount()
                    )
            );
        }
        return itemInRecipeDTOSet;
    }

    public static Set<ItemInRecipe> toEntitySet(Set<ItemInRecipeDTO> itemDTOSet) {
        if (itemDTOSet == null) {
            return new HashSet<>();
        }
        Set<ItemInRecipe> itemEntitySet = new HashSet<>();
        for (ItemInRecipeDTO item : itemDTOSet) {
            itemEntitySet.add(
                    new ItemInRecipe(
                            item.getRecipeId(),
                            ItemMapper.toEntity(item.getItem()),
                            item.getAmount()
                    )
            );
        }
        return itemEntitySet;
    }
}
