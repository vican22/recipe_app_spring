package com.vedran.recipe.converters;

import com.vedran.recipe.dto.CategoryDto;
import com.vedran.recipe.models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDtoToCategoryTest {

    public final Long LONG_VALUE = 1L;
    public final String DESCRIPTION = "description";

    CategoryDtoToCategory converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryDtoToCategory();
    }

    @Test
    public void testNullConvert() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyConvert() {
        assertNotNull(converter.convert(new CategoryDto()));
    }

    @Test
    void convert() {
        CategoryDto dto = new CategoryDto();
        dto.setId(LONG_VALUE);
        dto.setDescription(DESCRIPTION);

        Category category = converter.convert(dto);

        assertNotNull(category);
        assertEquals(LONG_VALUE, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }
}