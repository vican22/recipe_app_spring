package com.vedran.recipe.controllers;

import com.vedran.recipe.dto.RecipeDto;
import com.vedran.recipe.services.ImageService;
import com.vedran.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ImageControllerTest {

    ImageController imageController;

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations
                .initMocks(this);

        imageController = new ImageController(imageService, recipeService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(imageController)
                .build();
    }

    @Test
    public void getFormGet() throws Exception {
        //given
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);

        when(recipeService.findRecipeDtoById(anyLong()))
                .thenReturn(recipeDto);

        //when
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findRecipeDtoById(anyLong());
    }

    @Test
    void handleImagePost() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file",
                "testing.txt",
                "text/plain",
                "Spring Framework Guru".getBytes());

        mockMvc.perform(multipart("/recipe/1/image").file(mockMultipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));

        verify(imageService, times(1)).saveImageFile(anyLong(), any());
    }

    @Test
    public void renderImageFromDB() throws Exception {
        //given
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(1L);

        String s = "fake image text";
        Byte[] bytesBoxed = new Byte[s.getBytes().length];

        int i = 0;

        for (byte b : s.getBytes()) {
            bytesBoxed[i++] = b;
        }

        recipeDto.setImage(bytesBoxed);

        when(recipeService.findRecipeDtoById(anyLong()))
                .thenReturn(recipeDto);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();

        assertEquals(s.getBytes().length, responseBytes.length);
    }
}