package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.Recipe;
import com.eat.it.eatit.backend.dto.RecipeDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class RecipeMapperTest {

    private Recipe recipe;
    private RecipeDTO recipeDTO;

    @Test
    void testEntityToDTOConversion() {
        recipe = new Recipe();
        recipe.setName("test recipe");
        recipe.setDescription("test recipe desc");

        recipeDTO = RecipeMapper.toDTO(recipe);
        assertEquals(recipe.getName(), recipeDTO.getName());
        assertEquals(recipe.getDescription(), recipeDTO.getDescription());
    }

    @Test
    void testDTOToEntityConversion() {
        recipeDTO = new RecipeDTO();
        recipeDTO.setName("test recipe DTO");
        recipeDTO.setDescription("test recipe DTO desc");

        recipe = RecipeMapper.toEntity(recipeDTO);
        assertEquals(recipeDTO.getName(), recipe.getName());
        assertEquals(recipeDTO.getDescription(), recipe.getDescription());
    }

    @Test
    void testToDTOListConversion() {
        List<Recipe> recipeSet = List.of(
                new Recipe("recipe 1", "desc 1", new ArrayList<>(), new ArrayList<>(), 0),
                new Recipe("recipe 2", "desc 2", new ArrayList<>(), new ArrayList<>(), 0),
                new Recipe("recipe 3", "desc 3", new ArrayList<>(), new ArrayList<>(), 0)
        );
        List<RecipeDTO> recipeDTOExpectedSet = List.of(
                new RecipeDTO("recipe 1", "desc 1", new ArrayList<>(), new ArrayList<>(), 0),
                new RecipeDTO("recipe 2", "desc 2", new ArrayList<>(), new ArrayList<>(), 0),
                new RecipeDTO("recipe 3", "desc 3", new ArrayList<>(), new ArrayList<>(), 0)
        );
        List<RecipeDTO> recipeDTOSet = RecipeMapper.toDTOList(recipeSet);
        assertEquals(recipeDTOExpectedSet, recipeDTOSet);
    }

    @Test
    void testToEntityListConversion() {
        List<RecipeDTO> recipeDTOSet = List.of(
                new RecipeDTO("recipe 1", "desc 1", new ArrayList<>(), new ArrayList<>(), 0),
                new RecipeDTO("recipe 2", "desc 2", new ArrayList<>(), new ArrayList<>(), 0),
                new RecipeDTO("recipe 3", "desc 3", new ArrayList<>(), new ArrayList<>(), 0)
        );
        List<Recipe> recipeExpectedSet = List.of(
                new Recipe("recipe 1", "desc 1", new ArrayList<>(), new ArrayList<>(), 0),
                new Recipe("recipe 2", "desc 2", new ArrayList<>(), new ArrayList<>(), 0),
                new Recipe("recipe 3", "desc 3", new ArrayList<>(), new ArrayList<>(), 0)
        );
        List<Recipe> recipeSet = RecipeMapper.toEntityList(recipeDTOSet);
        assertEquals(recipeExpectedSet, recipeSet);
    }

    @Test
    void testConversionForNullEntityObject() {
        recipeDTO = RecipeMapper.toDTO(recipe);
        assertNotNull(recipeDTO);
    }

    @Test
    void testConversionForNullDTOObject() {
        recipe = RecipeMapper.toEntity(recipeDTO);
        assertNotNull(recipe);
    }

    @Test
    void testConversionForNullEntitySet() {
        List<RecipeDTO> recipeDTOSet = RecipeMapper.toDTOList(null);
        assertNotNull(recipeDTOSet);
        assertTrue(recipeDTOSet.isEmpty());
    }

    @Test
    void testConversionForDTOSet() {
        List<Recipe> recipeSet = RecipeMapper.toEntityList(null);
        assertNotNull(recipeSet);
        assertTrue(recipeSet.isEmpty());
    }

}