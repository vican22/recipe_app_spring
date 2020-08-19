package com.vedran.recipe.controllers;

import com.vedran.recipe.dto.IngredientDto;
import com.vedran.recipe.dto.RecipeDto;
import com.vedran.recipe.services.IngredientService;
import com.vedran.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    IngredientController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations
                .initMocks(this);

        controller = new IngredientController(recipeService, ingredientService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void testListIngredients() throws Exception {
        //given
        RecipeDto recipeDto = new RecipeDto();
        when(recipeService.findRecipeDtoById(anyLong()))
                .thenReturn(recipeDto);

        //when
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        //then
        verify(recipeService, times(1))
                .findRecipeDtoById(anyLong());
    }

    @Test
    public void testShowIngredient() throws Exception {
        //given
        IngredientDto dto = new IngredientDto();

        //when
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong()))
                .thenReturn(dto);

        //then
        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
    }
}