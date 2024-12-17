package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.Fridge;
import com.eat.it.eatit.backend.dto.FridgeDTO;

import java.util.ArrayList;
import java.util.List;

public class FridgeMapper {

    private FridgeMapper() {
    }

    public static FridgeDTO toDTO(Fridge fridge) {
        if (fridge == null) {
            return new FridgeDTO();
        }
        return new FridgeDTO(
                fridge.getId(),
                fridge.getOwnerId(),
                ItemInFridgeMapper.toDTOList(fridge.getItems()));
    }

    public static Fridge toEntity(FridgeDTO fridgeDTO) {
        if (fridgeDTO == null) {
            return new Fridge();
        }
        return new Fridge(
                fridgeDTO.getOwnerId(),
                ItemInFridgeMapper.toEntityList(fridgeDTO.getItems()));
    }

    public static List<FridgeDTO> toDTOList(List<Fridge> fridges) {
        if (fridges == null) {
            return new ArrayList<>();
        }
        return fridges.stream().map(FridgeMapper::toDTO).toList();
    }

    public static List<Fridge> toEntityList(List<FridgeDTO> fridgeDTOSet) {
        if (fridgeDTOSet == null) {
            return new ArrayList<>();
        }
        return fridgeDTOSet.stream().map(FridgeMapper::toEntity).toList();
    }
}
