package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.Fridge;
import com.eat.it.eatit.backend.dto.FridgeDTO;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
                ItemInFridgeMapper.toDTOSet(fridge.getItems()));
    }

    public static Fridge toEntity(FridgeDTO fridgeDTO) {
        if (fridgeDTO == null) {
            return new Fridge();
        }
        return new Fridge(
                fridgeDTO.getOwnerId(),
                ItemInFridgeMapper.toEntitySet(fridgeDTO.getItems()));
    }

    public static Set<FridgeDTO> toDTOSet(Set<Fridge> fridges) {
        if (fridges == null) {
            return new HashSet<>();
        }
        return fridges.stream().map(FridgeMapper::toDTO).collect(Collectors.toSet());
    }

    public static Set<Fridge> toEntitySet(Set<FridgeDTO> fridgeDTOSet) {
        if (fridgeDTOSet == null) {
            return new HashSet<>();
        }
        return fridgeDTOSet.stream().map(FridgeMapper::toEntity).collect(Collectors.toSet());
    }
}
