package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.dto.ItemDTO;

import java.util.ArrayList;
import java.util.List;

public class ItemMapper {

    private ItemMapper() {
    }

    public static ItemDTO toDTO(Item item) {
        if (item == null) {
            return new ItemDTO();
        }
        return new ItemDTO(
                item.getId(),
                item.getName(),
                item.getBarcode(),
                item.getCaloriesPer100g(),
                item.getProteins(),
                item.getFats(),
                item.getCarbs(),
                item.getItemType()
        );
    }

    public static Item toEntity(ItemDTO itemDTO) {
        if (itemDTO == null) {
            return new Item();
        }
        return new Item(
                itemDTO.getName(),
                itemDTO.getBarcode(),
                itemDTO.getCaloriesPer100g(),
                itemDTO.getProteins(),
                itemDTO.getFats(),
                itemDTO.getCarbs(),
                itemDTO.getItemType()
        );
    }

    public static List<ItemDTO> toDTOList(List<Item> items) {
        if (items == null) {
            return new ArrayList<>();
        }
        return items.stream().map(ItemMapper::toDTO).toList();
    }

    public static List<Item> toEntityList(List<ItemDTO> itemDTOSet) {
        if (itemDTOSet == null) {
            return new ArrayList<>();
        }
        return itemDTOSet.stream().map(ItemMapper::toEntity).toList();
    }
}
