package com.eat.it.eatit.backend.mappers;

import com.eat.it.eatit.backend.recipe.data.Recipe;
import com.eat.it.eatit.backend.recipe.data.RecipeDTO;
import com.eat.it.eatit.backend.recipe.data.RecipeMapper;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

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
    void testToDTOSetConversion() {
        Set<Recipe> recipeSet = Set.of(
                new Recipe("recipe 1", 1L, "desc 1", new HashSet<>(), new HashSet<>(), 0),
                new Recipe("recipe 2", 1L, "desc 2", new HashSet<>(), new HashSet<>(), 0),
                new Recipe("recipe 3", 1L, "desc 3", new HashSet<>(), new HashSet<>(), 0)
        );
        Set<RecipeDTO> recipeDTOExpectedSet = Set.of(
                new RecipeDTO("recipe 1", 1L, "desc 1", new HashSet<>(), new HashSet<>(), 0),
                new RecipeDTO("recipe 2", 1L, "desc 2", new HashSet<>(), new HashSet<>(), 0),
                new RecipeDTO("recipe 3", 1L, "desc 3", new HashSet<>(), new HashSet<>(), 0)
        );
        Set<RecipeDTO> recipeDTOSet = RecipeMapper.toDTOSet(recipeSet);
        assertEquals(recipeDTOExpectedSet, recipeDTOSet);
    }

    @Test
    void testToEntitySetConversion() {
        Set<RecipeDTO> recipeDTOSet = Set.of(
                new RecipeDTO("recipe 1", 1L, "desc 1", new HashSet<>(), new HashSet<>(), 0),
                new RecipeDTO("recipe 2", 1L, "desc 2", new HashSet<>(), new HashSet<>(), 0),
                new RecipeDTO("recipe 3", 1L, "desc 3", new HashSet<>(), new HashSet<>(), 0)
        );
        Set<Recipe> recipeExpectedSet = Set.of(
                new Recipe("recipe 1", 1L, "desc 1", new HashSet<>(), new HashSet<>(), 0),
                new Recipe("recipe 2", 1L, "desc 2", new HashSet<>(), new HashSet<>(), 0),
                new Recipe("recipe 3", 1L, "desc 3", new HashSet<>(), new HashSet<>(), 0)
        );
        Set<Recipe> recipeSet = RecipeMapper.toEntitySet(recipeDTOSet);
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
        Set<RecipeDTO> recipeDTOSet = RecipeMapper.toDTOSet(null);
        assertNotNull(recipeDTOSet);
        assertTrue(recipeDTOSet.isEmpty());
    }

    @Test
    void testConversionForDTOSet() {
        Set<Recipe> recipeSet = RecipeMapper.toEntitySet(null);
        assertNotNull(recipeSet);
        assertTrue(recipeSet.isEmpty());
    }

}