package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.Fridge;
import com.eat.it.eatit.backend.dto.FridgeDTO;

import java.util.HashSet;
import java.util.Set;

public class FridgeMapper {

    private FridgeMapper() {
    }

    public static FridgeDTO toDTO(Fridge fridge) {
        if (fridge == null) {
            return new FridgeDTO();
        }
        return new FridgeDTO(
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
        Set<FridgeDTO> fridgeDTOSet = new HashSet<>();
        for (Fridge fridge : fridges) {
            fridgeDTOSet.add(
                    new FridgeDTO(
                            fridge.getOwnerId(),
                            ItemInFridgeMapper.toDTOSet(fridge.getItems())
                    ));
        }
        return fridgeDTOSet;
    }

    public static Set<Fridge> toEntitySet(Set<FridgeDTO> fridgeDTOSet) {
        if (fridgeDTOSet == null) {
            return new HashSet<>();
        }
        Set<Fridge> fridgeSet = new HashSet<>();
        for (FridgeDTO fridgeDTO : fridgeDTOSet) {
            fridgeSet.add(
                    new Fridge(
                            fridgeDTO.getOwnerId(),
                            ItemInFridgeMapper.toEntitySet(fridgeDTO.getItems())
                    ));
        }
        return fridgeSet;
    }
}
