package com.vedran.recipe.converters;

import com.vedran.recipe.dto.UnitOfMeasureDto;
import com.vedran.recipe.models.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureDto implements Converter<UnitOfMeasure, UnitOfMeasureDto> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureDto convert(UnitOfMeasure source) {
        if (source == null) {
            return null;
        }

        final UnitOfMeasureDto uomc = new UnitOfMeasureDto();
        uomc.setId(source.getId());
        uomc.setDescription(source.getDescription());
        return uomc;
    }
}
