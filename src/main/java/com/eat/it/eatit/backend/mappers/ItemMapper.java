package com.eat.it.eatit.backend.mappers;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.dto.ItemDTO;

import java.util.HashSet;
import java.util.Set;

public class ItemMapper {

    private ItemMapper() {
    }

    public static ItemDTO toDTO(Item item) {
        if(item == null) {
            return null;
        }
        return new ItemDTO(
                item.getName(),
                item.getBarcode(),
                item.getCaloriesPer100g(),
                item.getProteinPer100G(),
                item.getFatPer100G(),
                item.getCarbsPer100G());
    }

    public static Item toEntity(ItemDTO itemDTO) {
        if(itemDTO == null) {
            return null;
        }
        return new Item(
                itemDTO.getName(),
                itemDTO.getBarcode(),
                itemDTO.getCaloriesPer100g(),
                itemDTO.getProteinPer100G(),
                itemDTO.getFatPer100G(),
                itemDTO.getCarbsPer100G());
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
                            item.getCarbsPer100G()));
        }
        return itemDTOSet;
    }

    public static Set<Item> toEntitySet(Set<ItemDTO> itemDTOs) {
        if(itemDTOs == null) {
            return new HashSet<>();
        }
        Set<Item> itemEntitySet = new HashSet<>();
        for(ItemDTO item: itemDTOs) {
            itemEntitySet.add(
                    new Item(
                            item.getName(),
                            item.getBarcode(),
                            item.getCaloriesPer100g(),
                            item.getProteinPer100G(),
                            item.getFatPer100G(),
                            item.getCarbsPer100G()));
        }
        return itemEntitySet;
    }
}
