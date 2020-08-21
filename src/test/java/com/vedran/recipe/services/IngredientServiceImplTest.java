package com.vedran.recipe.services;

import com.vedran.recipe.converters.IngredientDtoToIngredient;
import com.vedran.recipe.converters.IngredientToIngredientDto;
import com.vedran.recipe.converters.UnitOfMeasureDtoToUnitOfMeasure;
import com.vedran.recipe.converters.UnitOfMeasureToUnitOfMeasureDto;
import com.vedran.recipe.dto.IngredientDto;
import com.vedran.recipe.models.Ingredient;
import com.vedran.recipe.models.Recipe;
import com.vedran.recipe.repositories.RecipeRepository;
import com.vedran.recipe.repositories.UnitOfMeasureRepository;
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
    private final IngredientDtoToIngredient ingredientDtoToIngredient;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientService ingredientService;

    //init converters
    public IngredientServiceImplTest() {
        this.ingredientToIngredientDto = new IngredientToIngredientDto(new UnitOfMeasureToUnitOfMeasureDto());
        this.ingredientDtoToIngredient = new IngredientDtoToIngredient(new UnitOfMeasureDtoToUnitOfMeasure());
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations
                .initMocks(this);

        ingredientService = new IngredientServiceImpl(recipeRepository,
                ingredientToIngredientDto,
                ingredientDtoToIngredient,
                unitOfMeasureRepository);
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

    @Test
    public void testSaveRecipeDto() throws Exception {
        //given
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(3L);
        ingredientDto.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong()))
                .thenReturn(recipeOptional);

        when(recipeRepository.save(any()))
                .thenReturn(savedRecipe);

        //when
        IngredientDto savedDto = ingredientService.saveIngredientDto(ingredientDto);

        //then
        assertEquals(3L, savedDto.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void testDeleteById() throws Exception {
        //given
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        recipe.addIngredient(ingredient);
        ingredient.setRecipe(recipe);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong()))
                .thenReturn(recipeOptional);

        //when
        ingredientService.deleteById(1L, 3L);

        //then
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}





























