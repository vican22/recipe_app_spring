package com.vedran.recipe.services;

import com.vedran.recipe.dto.UnitOfMeasureDto;
import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureDto> listAllUoms();
}
