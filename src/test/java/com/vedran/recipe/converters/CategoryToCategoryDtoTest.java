package com.vedran.recipe.converters;

import com.vedran.recipe.dto.CategoryDto;
import com.vedran.recipe.models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryDtoTest {

    public final Long LONG_VALUE= 1L;
    public final String DESCRIPTION = "description";

    CategoryToCategoryDto converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryToCategoryDto();
    }

    @Test
    public void testNullConverter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyConverter() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    void convert() {
        Category category = new Category();
        category.setId(LONG_VALUE);
        category.setDescription(DESCRIPTION);

        CategoryDto dto = converter.convert(category);

        assertNotNull(dto);
        assertEquals(LONG_VALUE, dto.getId());
        assertEquals(DESCRIPTION, dto.getDescription());
    }
}