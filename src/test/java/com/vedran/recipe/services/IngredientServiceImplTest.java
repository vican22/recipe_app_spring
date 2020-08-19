package com.vedran.recipe.services;

import com.vedran.recipe.converters.IngredientToIngredientDto;
import com.vedran.recipe.converters.UnitOfMeasureToUnitOfMeasureDto;
import com.vedran.recipe.dto.IngredientDto;
import com.vedran.recipe.models.Ingredient;
import com.vedran.recipe.models.Recipe;
import com.vedran.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    private final IngredientToIngredientDto ingredientToIngredientDto;

    @Mock
    RecipeRepository recipeRepository;

    IngredientService ingredientService;

    //init converters
    public IngredientServiceImplTest() {
        this.ingredientToIngredientDto = new IngredientToIngredientDto(new UnitOfMeasureToUnitOfMeasureDto());
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations
                .initMocks(this);

        ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientDto);
    }

    @Test
    void findByRecipeIdAndIngredientId() {
    }

    @Test
    public void findByRecipeIdAndIdHappyPath() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong()))
                .thenReturn(recipeOptional);

        //when
        IngredientDto ingredientDto = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        //then
        assertEquals(3L, ingredientDto.getId());
        assertEquals(1L, ingredientDto.getRecipeId());
        verify(recipeRepository, times(1))
                .findById(anyLong());
    }
}





























