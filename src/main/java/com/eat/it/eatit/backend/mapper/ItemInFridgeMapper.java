package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.ItemInFridge;
import com.eat.it.eatit.backend.dto.ItemInFridgeDTO;

import java.util.ArrayList;
import java.util.List;

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

    public static List<ItemInFridgeDTO> toDTOList(List<ItemInFridge> items) {
        if (items == null) {
            return new ArrayList<>();
        }
        return items.stream().map(ItemInFridgeMapper::toDTO).toList();
    }

    public static List<ItemInFridge> toEntityList(List<ItemInFridgeDTO> itemDTOSet) {
        if (itemDTOSet == null) {
            return new ArrayList<>();
        }
        return itemDTOSet.stream().map(ItemInFridgeMapper::toEntity).toList();
    }
}
