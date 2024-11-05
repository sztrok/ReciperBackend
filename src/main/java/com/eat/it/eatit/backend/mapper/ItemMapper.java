package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.dto.ItemDTO;

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
                item.getProteins(),
                item.getFatPer100G(),
                item.getCarbsPer100G(),
                item.getItemType()
        );
    }

    public static Item toEntity(ItemDTO itemDTO) {
        if(itemDTO == null) {
            return new Item();
        }
        return new Item(
                itemDTO.getName(),
                itemDTO.getBarcode(),
                itemDTO.getCaloriesPer100g(),
                itemDTO.getProteins(),
                itemDTO.getFatPer100G(),
                itemDTO.getCarbsPer100G(),
                itemDTO.getItemType()
        );
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
                            item.getProteins(),
                            item.getFatPer100G(),
                            item.getCarbsPer100G(),
                            item.getItemType()
                    )
            );
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
                            item.getProteins(),
                            item.getFatPer100G(),
                            item.getCarbsPer100G(),
                            item.getItemType()
                    )
            );
        }
        return itemEntitySet;
    }


}
