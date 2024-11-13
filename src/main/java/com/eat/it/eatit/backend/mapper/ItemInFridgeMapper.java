package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.ItemInFridge;
import com.eat.it.eatit.backend.dto.ItemInFridgeDTO;

import java.util.HashSet;
import java.util.Set;

public class ItemInFridgeMapper {

    private ItemInFridgeMapper() {
    }

    public static ItemInFridgeDTO toDTO(ItemInFridge itemInFridge) {
        ItemInFridgeDTO itemInFridgeDTO = new ItemInFridgeDTO();
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
        Set<ItemInFridgeDTO> itemInFridgeDTOSet = new HashSet<>();
        for (ItemInFridge item : items) {
            itemInFridgeDTOSet.add(
                    new ItemInFridgeDTO(
                            item.getId(),
                            item.getFridgeId(),
                            ItemMapper.toDTO(item.getItem()),
                            item.getAmount()
                    )
            );
        }
        return itemInFridgeDTOSet;
    }

    public static Set<ItemInFridge> toEntitySet(Set<ItemInFridgeDTO> itemDTOSet) {
        if (itemDTOSet == null) {
            return new HashSet<>();
        }
        Set<ItemInFridge> itemEntitySet = new HashSet<>();
        for (ItemInFridgeDTO item : itemDTOSet) {
            itemEntitySet.add(
                    new ItemInFridge(
                            item.getFridgeId(),
                            ItemMapper.toEntity(item.getItem()),
                            item.getAmount()
                    )
            );
        }
        return itemEntitySet;
    }
}
