package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.ItemInFridge;
import com.eat.it.eatit.backend.dto.ItemInFridgeDTO;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemInFridgeMapper {

    private ItemInFridgeMapper() {
    }

    public static ItemInFridgeDTO toDTO(ItemInFridge itemInFridge) {
        ItemInFridgeDTO itemInFridgeDTO = new ItemInFridgeDTO();
        itemInFridgeDTO.setId(itemInFridge.getId());
        itemInFridgeDTO.setFridgeId(itemInFridge.getFridgeId());
        itemInFridgeDTO.setItem(ItemMapper.toDTO(itemInFridge.getItem()));
        itemInFridgeDTO.setAmount(itemInFridge.getAmount());
        return itemInFridgeDTO;
    }

    public static ItemInFridge toEntity(ItemInFridgeDTO itemInFridgeDTO) {
        ItemInFridge itemInFridge = new ItemInFridge();
        itemInFridge.setFridgeId(itemInFridgeDTO.getFridgeId());
        itemInFridge.setItem(ItemMapper.toEntity(itemInFridgeDTO.getItem()));
        itemInFridge.setAmount(itemInFridgeDTO.getAmount());
        return itemInFridge;
    }

    public static Set<ItemInFridgeDTO> toDTOSet(Set<ItemInFridge> items) {
        if (items == null) {
            return new HashSet<>();
        }
        return items.stream().map(ItemInFridgeMapper::toDTO).collect(Collectors.toSet());
    }

    public static Set<ItemInFridge> toEntitySet(Set<ItemInFridgeDTO> itemDTOSet) {
        if (itemDTOSet == null) {
            return new HashSet<>();
        }
        return itemDTOSet.stream().map(ItemInFridgeMapper::toEntity).collect(Collectors.toSet());
    }
}
