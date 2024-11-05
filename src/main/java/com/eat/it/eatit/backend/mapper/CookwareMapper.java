package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.Cookware;
import com.eat.it.eatit.backend.dto.CookwareDTO;

import java.util.HashSet;
import java.util.Set;

public class CookwareMapper {

    private CookwareMapper() {
    }

    public static CookwareDTO toDTO(Cookware cookware) {
        if(cookware == null) {
            return new CookwareDTO();
        }
        return new CookwareDTO(cookware.getName());
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
        Set<CookwareDTO> cookwareDTOSet = new HashSet<>();
        for(Cookware cookware : cookwareSet) {
            cookwareDTOSet.add(new CookwareDTO(cookware.getName()));
        }
        return cookwareDTOSet;
    }

    public static Set<Cookware> toEntitySet(Set<CookwareDTO> cookwareDTOSet) {
        if(cookwareDTOSet == null) {
            return new HashSet<>();
        }
        Set<Cookware> cookwareEntitySet = new HashSet<>();
        for(CookwareDTO cookwareDTO : cookwareDTOSet) {
            cookwareEntitySet.add(new Cookware(cookwareDTO.getName()));
        }
        return cookwareEntitySet;
    }
}
