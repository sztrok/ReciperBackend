package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.Cookware;
import com.eat.it.eatit.backend.dto.CookwareDTO;

import java.util.ArrayList;
import java.util.List;

public class CookwareMapper {

    private CookwareMapper() {
    }

    public static CookwareDTO toDTO(Cookware cookware) {
        if(cookware == null) {
            return new CookwareDTO();
        }
        return new CookwareDTO(
                cookware.getId(),
                cookware.getName());
    }

    public static Cookware toEntity(CookwareDTO cookwareDTO) {
        if(cookwareDTO == null) {
            return new Cookware();
        }
        return new Cookware(cookwareDTO.getName());
    }

    public static List<CookwareDTO> toDTOList(List<Cookware> cookwareSet) {
        if(cookwareSet == null) {
            return new ArrayList<>();
        }
        return cookwareSet.stream()
                .map(CookwareMapper::toDTO)
                .toList();
    }

    public static List<Cookware> toEntityList(List<CookwareDTO> cookwareDTOSet) {
        if(cookwareDTOSet == null) {
            return new ArrayList<>();
        }
        return cookwareDTOSet.stream().map(CookwareMapper::toEntity).toList();
    }
}
