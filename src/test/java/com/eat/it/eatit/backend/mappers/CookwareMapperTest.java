package com.eat.it.eatit.backend.mappers;

import com.eat.it.eatit.backend.cookware.data.Cookware;
import com.eat.it.eatit.backend.cookware.data.CookwareDTO;
import com.eat.it.eatit.backend.cookware.data.CookwareMapper;
import org.junit.jupiter.api.Test;

import java.util.Set;

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
    void testToDTOSetConversion() {
        Set<Cookware> cookwareSet = Set.of(
                new Cookware("test 1"),
                new Cookware("test 2"),
                new Cookware("test 3")
        );
        Set<CookwareDTO> cookwareDTOExpectedSet = Set.of(
                new CookwareDTO("test 1"),
                new CookwareDTO("test 2"),
                new CookwareDTO("test 3")
        );
        Set<CookwareDTO> cookwareDTOSet = CookwareMapper.toDTOSet(cookwareSet);
        assertEquals(cookwareDTOExpectedSet, cookwareDTOSet);
    }

    @Test
    void testToEntitySetConversion() {
        Set<CookwareDTO> cookwareDTOSet = Set.of(
                new CookwareDTO("test dto 1"),
                new CookwareDTO("test dto 2"),
                new CookwareDTO("test dto 3")
        );
        Set<Cookware> cookwareExpectedSet = Set.of(
                new Cookware("test dto 1"),
                new Cookware("test dto 2"),
                new Cookware("test dto 3")
        );
        Set<Cookware> cookwareSet = CookwareMapper.toEntitySet(cookwareDTOSet);
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
        Set<CookwareDTO> cookwareDTOSet = CookwareMapper.toDTOSet(null);
        assertNotNull(cookwareDTOSet);
        assertTrue(cookwareDTOSet.isEmpty());
    }

    @Test
    void testConversionForDTOSet() {
        Set<Cookware> cookwareSet = CookwareMapper.toEntitySet(null);
        assertNotNull(cookwareSet);
        assertTrue(cookwareSet.isEmpty());
    }

}