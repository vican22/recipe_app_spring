package com.vedran.recipe.converters;

import com.vedran.recipe.dto.UnitOfMeasureDto;
import com.vedran.recipe.models.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureDtoToUnitOfMeasureTest {

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = 1L;

    UnitOfMeasureDtoToUnitOfMeasure converter;


    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureDtoToUnitOfMeasure();
    }

    @Test
    public void testNullParameter() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasureDto()));
    }


    @Test
    void convert() {
        //given
        UnitOfMeasureDto unitOfMeasureDto = new UnitOfMeasureDto();
        unitOfMeasureDto.setId(LONG_VALUE);
        unitOfMeasureDto.setDescription(DESCRIPTION);

        //when
        UnitOfMeasure uom = converter.convert(unitOfMeasureDto);

        //then
        assertNotNull(uom);
        assertEquals(LONG_VALUE, uom.getId());
        assertEquals(DESCRIPTION, uom.getDescription());
    }
}