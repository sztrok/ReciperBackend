package com.eat.it.eatit.backend.item.data;

import com.eat.it.eatit.backend.fridge.data.FridgeMapper;
import com.eat.it.eatit.backend.recipe.data.RecipeMapper;

import java.util.HashSet;
import java.util.Set;

public class ItemMapper {

    private ItemMapper() {
    }

    public static ItemDTO toDTO(Item item) {
        if(item == null) {
            return new ItemDTO();
        }
        return new ItemDTO(
                item.getName(),
                item.getBarcode(),
                item.getCaloriesPer100g(),
                item.getProteinPer100G(),
                item.getFatPer100G(),
                item.getCarbsPer100G(),
                item.getAmount(),
                RecipeMapper.toDTOSet(item.getRecipesContainingItem()),
                FridgeMapper.toDTOSet(item.getFridgesContainingItem()));
    }

    public static Item toEntity(ItemDTO itemDTO) {
        if(itemDTO == null) {
            return new Item();
        }
        return new Item(
                itemDTO.getName(),
                itemDTO.getBarcode(),
                itemDTO.getCaloriesPer100g(),
                itemDTO.getProteinPer100G(),
                itemDTO.getFatPer100G(),
                itemDTO.getCarbsPer100G(),
                itemDTO.getAmount(),
                RecipeMapper.toEntitySet(itemDTO.getRecipesContainingItem()),
                FridgeMapper.toEntitySet(itemDTO.getFridgesContainingItem()));
    }

    public static Set<ItemDTO> toDTOSet(Set<Item> items) {
        if(items == null) {
            return new HashSet<>();
        }
        Set<ItemDTO> itemDTOSet = new HashSet<>();
        for(Item item: items) {
            itemDTOSet.add(
                    new ItemDTO(
                            item.getName(),
                            item.getBarcode(),
                            item.getCaloriesPer100g(),
                            item.getProteinPer100G(),
                            item.getFatPer100G(),
                            item.getCarbsPer100G(),
                            item.getAmount(),
                            RecipeMapper.toDTOSet(item.getRecipesContainingItem()),
                            FridgeMapper.toDTOSet(item.getFridgesContainingItem())));
        }
        return itemDTOSet;
    }

    public static Set<Item> toEntitySet(Set<ItemDTO> itemDTOSet) {
        if(itemDTOSet == null) {
            return new HashSet<>();
        }
        Set<Item> itemEntitySet = new HashSet<>();
        for(ItemDTO item: itemDTOSet) {
            itemEntitySet.add(
                    new Item(
                            item.getName(),
                            item.getBarcode(),
                            item.getCaloriesPer100g(),
                            item.getProteinPer100G(),
                            item.getFatPer100G(),
                            item.getCarbsPer100G(),
                            item.getAmount(),
                            RecipeMapper.toEntitySet(item.getRecipesContainingItem()),
                            FridgeMapper.toEntitySet(item.getFridgesContainingItem())));
        }
        return itemEntitySet;
    }
}
