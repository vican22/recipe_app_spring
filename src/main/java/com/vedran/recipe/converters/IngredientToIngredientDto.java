package com.vedran.recipe.converters;

import com.vedran.recipe.dto.IngredientDto;
import com.vedran.recipe.models.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientDto implements Converter<Ingredient, IngredientDto> {

    private final UnitOfMeasureToUnitOfMeasureDto unitOfMeasureToUnitOfMeasureDto;

    public IngredientToIngredientDto(UnitOfMeasureToUnitOfMeasureDto unitOfMeasureToUnitOfMeasureDto) {
        this.unitOfMeasureToUnitOfMeasureDto = unitOfMeasureToUnitOfMeasureDto;
    }

    @Nullable
    @Override
    public IngredientDto convert(Ingredient source) {
        if (source == null) {
            return null;
        }

        IngredientDto dto = new IngredientDto();
        dto.setId(source.getId());
        dto.setAmount(source.getAmount());
        dto.setDescription(source.getDescription());
        dto.setUnitOfMeasure(unitOfMeasureToUnitOfMeasureDto.convert(source.getUnitOfMeasure()));
        return dto;
    }
}
