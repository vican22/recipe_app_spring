package com.vedran.recipe.converters;

import com.vedran.recipe.dto.RecipeDto;
import com.vedran.recipe.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeToRecipeDtoTest {

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

    RecipeToRecipeDto converter;

    @BeforeEach
    void setUp() {
        converter = new RecipeToRecipeDto(new CategoryToCategoryDto(),
                new IngredientToIngredientDto(new UnitOfMeasureToUnitOfMeasureDto()),
                new NotesToNotesDto());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    void convert() {
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setCookTime(COOK_TIME);
        recipe.setPrepTime(PREP_TIME);
        recipe.setDescription(DESCRIPTION);
        recipe.setDirections(DIRECTIONS);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);

        Notes notes = new Notes();
        notes.setId(NOTES_ID_1);

        recipe.setNotes(notes);

        Category cat1 = new Category();
        cat1.setId(CAT_ID_1);

        Category cat2 = new Category();
        cat2.setId(CAT_ID_2);

        recipe.getCategories().add(cat1);
        recipe.getCategories().add(cat2);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(INGRED_ID_1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGRED_ID_2);

        recipe.getIngredients().add(ingredient1);
        recipe.getIngredients().add(ingredient2);

        //when
        RecipeDto dto = converter.convert(recipe);

        //then
        assertNotNull(dto);
        assertEquals(RECIPE_ID, dto.getId());
        assertEquals(COOK_TIME, dto.getCookTime());
        assertEquals(PREP_TIME, dto.getPrepTime());
        assertEquals(DESCRIPTION, dto.getDescription());
        assertEquals(DIFFICULTY, dto.getDifficulty());
        assertEquals(DIRECTIONS, dto.getDirections());
        assertEquals(SERVINGS, dto.getServings());
        assertEquals(SOURCE, dto.getSource());
        assertEquals(URL, dto.getUrl());
        assertEquals(NOTES_ID_1, dto.getNotes().getId());
        assertEquals(2, dto.getCategories().size());
        assertEquals(2, dto.getIngredients().size());
    }
}