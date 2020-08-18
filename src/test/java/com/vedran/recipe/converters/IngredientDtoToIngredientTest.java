package com.vedran.recipe.converters;

import com.vedran.recipe.dto.IngredientDto;
import com.vedran.recipe.dto.UnitOfMeasureDto;
import com.vedran.recipe.models.Ingredient;
import com.vedran.recipe.models.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientDtoToIngredientTest {

    public static final Recipe RECIPE = new Recipe();
    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final String DESCRIPTION = "Cheeseburger";
    public static final Long ID_VALUE = 1L;
    public static final Long UOM_ID = 2L;

    IngredientDtoToIngredient converter;

    @BeforeEach
    void setUp() {
        converter = new IngredientDtoToIngredient(new UnitOfMeasureDtoToUnitOfMeasure());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new IngredientDto()));
    }

    @Test
    void convert() {
        //given
        IngredientDto dto = new IngredientDto();
        dto.setId(ID_VALUE);
        dto.setAmount(AMOUNT);
        dto.setDescription(DESCRIPTION);
        UnitOfMeasureDto unitOfMeasureDto = new UnitOfMeasureDto();
        unitOfMeasureDto.setId(UOM_ID);
        dto.setUnitOfMeasure(unitOfMeasureDto);

        //when
        Ingredient ingredient = converter.convert(dto);

        //then
        assertNotNull(ingredient);
        assertNotNull(ingredient.getUnitOfMeasure());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(UOM_ID, ingredient.getUnitOfMeasure().getId());
    }

    @Test
    public void convertWithNullUOM() {
        //given
        IngredientDto dto = new IngredientDto();
        dto.setId(ID_VALUE);
        dto.setAmount(AMOUNT);
        dto.setDescription(DESCRIPTION);
        UnitOfMeasureDto unitOfMeasureDto = new UnitOfMeasureDto();

        //given
        Ingredient ingredient = converter.convert(dto);

        //then
        assertNotNull(ingredient);
        assertNull(ingredient.getUnitOfMeasure());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
    }
}