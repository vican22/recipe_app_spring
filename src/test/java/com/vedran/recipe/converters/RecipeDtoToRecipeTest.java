package com.vedran.recipe.converters;

import com.vedran.recipe.dto.CategoryDto;
import com.vedran.recipe.dto.IngredientDto;
import com.vedran.recipe.dto.NotesDto;
import com.vedran.recipe.dto.RecipeDto;
import com.vedran.recipe.models.Difficulty;
import com.vedran.recipe.models.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;

import static org.junit.jupiter.api.Assertions.*;

class RecipeDtoToRecipeTest {

    public static final Long RECIPE_ID = 1L;
    public static final Integer COOK_TIME = Integer.valueOf("5");
    public static final Integer PREP_TIME = Integer.valueOf("7");
    public static final String DESCRIPTION = "My Recipe";
    public static final String DIRECTIONS = "Directions";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Integer SERVINGS = Integer.valueOf("3");
    public static final String SOURCE = "Source";
    public static final String URL = "www.google.com";
    public static final Long CAT_ID_1 = 1L;
    public static final Long CAT_ID_2 = 2L;
    public static final Long INGRED_ID_1 = 3L;
    public static final Long INGRED_ID_2 = 4L;
    public static final Long NOTES_ID_1 = 6L;

    RecipeDtoToRecipe converter;

    @BeforeEach
    void setUp() {
        converter = new RecipeDtoToRecipe(new CategoryDtoToCategory(),
                new IngredientDtoToIngredient(new UnitOfMeasureDtoToUnitOfMeasure()),
                new NotesDtoToNotes());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            assertNotNull(converter.convert(new RecipeDto()));
        });

        assertNull(exception.getMessage());
    }

    @Test
    void convert() {
        //given
        RecipeDto dto = new RecipeDto();
        dto.setId(RECIPE_ID);
        dto.setCookTime(COOK_TIME);
        dto.setPrepTime(PREP_TIME);
        dto.setDescription(DESCRIPTION);
        dto.setDifficulty(DIFFICULTY);
        dto.setDirections(DIRECTIONS);
        dto.setServings(SERVINGS);
        dto.setSource(SOURCE);
        dto.setUrl(URL);

        NotesDto notesDto = new NotesDto();
        notesDto.setId(NOTES_ID_1);

        dto.setNotes(notesDto);

        CategoryDto categoryDto1 = new CategoryDto();
        categoryDto1.setId(CAT_ID_1);

        CategoryDto categoryDto2 = new CategoryDto();
        categoryDto2.setId(CAT_ID_2);

        dto.getCategories().add(categoryDto1);
        dto.getCategories().add(categoryDto2);

        IngredientDto ingredientDto1 = new IngredientDto();
        ingredientDto1.setId(INGRED_ID_1);

        IngredientDto ingredientDto2 = new IngredientDto();
        ingredientDto2.setId(INGRED_ID_2);

        dto.getIngredients().add(ingredientDto1);
        dto.getIngredients().add(ingredientDto2);

        //when
        Recipe recipe = converter.convert(dto);

        //then
        assertNotNull(recipe);
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(NOTES_ID_1, recipe.getNotes().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
    }
}