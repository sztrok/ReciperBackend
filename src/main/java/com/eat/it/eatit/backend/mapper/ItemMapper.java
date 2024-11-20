package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.dto.ItemDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                item.getFatPer100G(),
                item.getCarbsPer100G(),
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
                itemDTO.getFatPer100G(),
                itemDTO.getCarbsPer100G(),
                itemDTO.getItemType()
        );
    }

    public static List<ItemDTO> toDTOList(List<Item> items) {
        if (items == null) {
            return new ArrayList<>();
        }
        return items.stream().map(ItemMapper::toDTO).collect(Collectors.toList());
    }

    public static List<Item> toEntityList(List<ItemDTO> itemDTOSet) {
        if (itemDTOSet == null) {
            return new ArrayList<>();
        }
        return itemDTOSet.stream().map(ItemMapper::toEntity).collect(Collectors.toList());
    }
}
