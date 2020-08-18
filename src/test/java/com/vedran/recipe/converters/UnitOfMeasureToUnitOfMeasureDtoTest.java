package com.vedran.recipe.converters;

import com.vedran.recipe.dto.UnitOfMeasureDto;
import com.vedran.recipe.models.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureToUnitOfMeasureDtoTest {

    public final String DESCRIPTION = "description";
    public final Long LONG_VALUE = 1L;

    UnitOfMeasureToUnitOfMeasureDto converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureDto();
    }

    @Test
    public void testNullConverter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyConverter() {
       assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    void convert() {
        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(LONG_VALUE);
        uom.setDescription(DESCRIPTION);

        //when
        UnitOfMeasureDto dto = converter.convert(uom);

        //then
        assertNotNull(dto);
        assertEquals(LONG_VALUE, dto.getId());
        assertEquals(DESCRIPTION, dto.getDescription());
    }
}