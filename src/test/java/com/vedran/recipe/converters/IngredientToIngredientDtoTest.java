package com.vedran.recipe.converters;

import com.vedran.recipe.dto.IngredientDto;
import com.vedran.recipe.models.Ingredient;
import com.vedran.recipe.models.Recipe;
import com.vedran.recipe.models.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientToIngredientDtoTest {

    public static final Recipe RECIPE = new Recipe();
    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final String DESCRIPTION = "Cheeseburger";
    public static final Long ID_VALUE = 1L;
    public static final Long UOM_ID = 2L;

    IngredientToIngredientDto converter;

    @BeforeEach
    void setUp() {
        converter = new IngredientToIngredientDto(new UnitOfMeasureToUnitOfMeasureDto());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Ingredient()));
    }

    @Test
    void convert() {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(UOM_ID);
        ingredient.setUnitOfMeasure(uom);


        //when
        IngredientDto dto = converter.convert(ingredient);

        //then
        assertNotNull(dto);
        assertNotNull(dto.getUnitOfMeasure());
        assertEquals(ID_VALUE, dto.getId());
        assertEquals(AMOUNT, dto.getAmount());
        assertEquals(DESCRIPTION, dto.getDescription());
    }

    @Test
    public void convertUOMNull() {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);
        UnitOfMeasure uom = new UnitOfMeasure();

        //when
        IngredientDto dto = converter.convert(ingredient);

        //then
        assertNotNull(dto);
        assertNull(dto.getUnitOfMeasure());
        assertEquals(ID_VALUE, dto.getId());
        assertEquals(AMOUNT, dto.getAmount());
        assertEquals(DESCRIPTION, dto.getDescription());
    }
}