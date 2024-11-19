package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.Cookware;
import com.eat.it.eatit.backend.dto.CookwareDTO;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    public static Set<CookwareDTO> toDTOSet(Set<Cookware> cookwareSet) {
        if(cookwareSet == null) {
            return new HashSet<>();
        }
        return cookwareSet.stream()
                .map(CookwareMapper::toDTO)
                .collect(Collectors.toSet());
    }

    public static Set<Cookware> toEntitySet(Set<CookwareDTO> cookwareDTOSet) {
        if(cookwareDTOSet == null) {
            return new HashSet<>();
        }
        return cookwareDTOSet.stream().map(CookwareMapper::toEntity).collect(Collectors.toSet());
    }
}
