package com.vedran.recipe.services;

import com.vedran.recipe.converters.RecipeDtoToRecipe;
import com.vedran.recipe.converters.RecipeToRecipeDto;
import com.vedran.recipe.exceptions.NotFoundException;
import com.vedran.recipe.models.Recipe;
import com.vedran.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeDtoToRecipe dtoToRecipeConverter;
    @Mock
    RecipeToRecipeDto recipeToRecipeDtoConverter;
    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations
                .initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, dtoToRecipeConverter, recipeToRecipeDtoConverter);
    }

    @Test
    public void getRecipeByIdTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong()))
                .thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);

        assertNotNull(recipeReturned);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    void getRecipesTest() {

        Recipe recipe = new Recipe();
        HashSet recipeData = new HashSet();
        recipeData.add(recipe);

        //mock data returned from repository
        when(recipeRepository.findAll())
                .thenReturn(recipeData);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1);

        verify(recipeRepository, times(1)).findAll();

    }

    @Test
    public void testDeleteById() throws Exception {

        //given
        Long idToDelete = 2L;

        //when
        recipeService.deleteById(idToDelete);

        //no 'when', since method has void return type

        //then
        verify(recipeRepository, times(1))
                .deleteById(anyLong());
    }

    @Test
    public void getRecipeByIdTestNotFound() throws Exception {
        //given
        Optional<Recipe> recipeOptional = Optional.empty();

        when(recipeRepository.findById(anyLong()))
                .thenReturn(recipeOptional);

        //when
        NotFoundException notFoundException = assertThrows(
                NotFoundException.class, () -> recipeService.findById(1L),
                ""
        );

        //then
        assertTrue(notFoundException.getMessage().contains("Recipe Not Found"));
    }
}