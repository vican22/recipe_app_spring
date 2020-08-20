package com.vedran.recipe.controllers;

import com.vedran.recipe.dto.IngredientDto;
import com.vedran.recipe.dto.RecipeDto;
import com.vedran.recipe.services.IngredientService;
import com.vedran.recipe.services.RecipeService;
import com.vedran.recipe.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    IngredientController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations
                .initMocks(this);

        controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);

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

    @Test
    public void testUpdateIngredientForm() throws Exception {
        //given
        IngredientDto ingredientDto = new IngredientDto();

        //when
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong()))
                .thenReturn(ingredientDto);
        when(unitOfMeasureService.listAllUoms())
                .thenReturn(new HashSet<>());

        //then
        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        //given
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(3L);
        ingredientDto.setRecipeId(2L);

        //when
        when(ingredientService.saveIngredientDto(any()))
                .thenReturn(ingredientDto);

        //then
        mockMvc.perform(post("/recipe/2/ingredient")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "")
        .param("description", "some string"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));

    }
}