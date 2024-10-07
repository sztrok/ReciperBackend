package com.eat.it.eatit.backend.fridge.data;

import com.eat.it.eatit.backend.item.data.ItemMapper;

public class FridgeMapper {

    private FridgeMapper() {
    }

    public static FridgeDTO toDTO(Fridge fridge) {
        if(fridge == null) {
            return new FridgeDTO();
        }
        return new FridgeDTO(
                fridge.getOwnerId(),
                ItemMapper.toDTOSet(fridge.getItems()));
    }

    public static Fridge toEntity(FridgeDTO fridgeDTO) {
        if(fridgeDTO == null) {
            return new Fridge();
        }
        return new Fridge(
                fridgeDTO.getOwnerId(),
                ItemMapper.toEntitySet(fridgeDTO.getItems()));
    }
}
