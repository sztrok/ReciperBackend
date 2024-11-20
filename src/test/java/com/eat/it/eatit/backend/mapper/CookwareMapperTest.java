package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.Cookware;
import com.eat.it.eatit.backend.dto.CookwareDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CookwareMapperTest {

    private Cookware cookware;
    private CookwareDTO cookwareDTO;

    @Test
    void testEntityToDTOConversion() {
        cookware = new Cookware();
        cookware.setName("test cookware");

        cookwareDTO = CookwareMapper.toDTO(cookware);
        assertEquals(cookware.getName(), cookwareDTO.getName());
    }

    @Test
    void testDTOToEntityConversion() {
        cookwareDTO = new CookwareDTO();
        cookwareDTO.setName("test dto cookware");
        cookware = CookwareMapper.toEntity(cookwareDTO);
        assertEquals(cookwareDTO.getName(), cookware.getName());
    }

    @Test
    void testToDTOListConversion() {
        List<Cookware> cookwareSet = List.of(
                new Cookware("test 1"),
                new Cookware("test 2"),
                new Cookware("test 3")
        );
        List<CookwareDTO> cookwareDTOExpectedSet = List.of(
                new CookwareDTO("test 1"),
                new CookwareDTO("test 2"),
                new CookwareDTO("test 3")
        );
        List<CookwareDTO> cookwareDTOSet = CookwareMapper.toDTOList(cookwareSet);
        assertEquals(cookwareDTOExpectedSet, cookwareDTOSet);
    }

    @Test
    void testToEntityListConversion() {
        List<CookwareDTO> cookwareDTOSet = List.of(
                new CookwareDTO("test dto 1"),
                new CookwareDTO("test dto 2"),
                new CookwareDTO("test dto 3")
        );
        List<Cookware> cookwareExpectedSet = List.of(
                new Cookware("test dto 1"),
                new Cookware("test dto 2"),
                new Cookware("test dto 3")
        );
        List<Cookware> cookwareSet = CookwareMapper.toEntityList(cookwareDTOSet);
        assertEquals(cookwareExpectedSet, cookwareSet);
    }

    @Test
    void testConversionForNullEntityObject() {
        cookwareDTO = CookwareMapper.toDTO(cookware);
        assertNotNull(cookwareDTO);
    }

    @Test
    void testConversionForNullDTOObject() {
        cookware = CookwareMapper.toEntity(cookwareDTO);
        assertNotNull(cookware);
    }

    @Test
    void testConversionForNullEntitySet() {
        List<CookwareDTO> cookwareDTOSet = CookwareMapper.toDTOList(null);
        assertNotNull(cookwareDTOSet);
        assertTrue(cookwareDTOSet.isEmpty());
    }

    @Test
    void testConversionForDTOSet() {
        List<Cookware> cookwareSet = CookwareMapper.toEntityList(null);
        assertNotNull(cookwareSet);
        assertTrue(cookwareSet.isEmpty());
    }

}