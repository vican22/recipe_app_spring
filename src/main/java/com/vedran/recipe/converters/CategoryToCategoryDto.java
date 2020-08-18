package com.vedran.recipe.converters;

import com.vedran.recipe.dto.CategoryDto;
import com.vedran.recipe.models.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryDto implements Converter<Category, CategoryDto> {

    @Synchronized
    @Nullable
    @Override
    public CategoryDto convert(Category source) {
        if (source == null) {
            return null;
        }

        final CategoryDto dto = new CategoryDto();
        dto.setId(source.getId());
        dto.setDescription(source.getDescription());
        return dto;
    }
}
