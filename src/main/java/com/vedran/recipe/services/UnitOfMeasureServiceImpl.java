package com.vedran.recipe.services;

import com.vedran.recipe.converters.UnitOfMeasureToUnitOfMeasureDto;
import com.vedran.recipe.dto.UnitOfMeasureDto;
import com.vedran.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureDto unitOfMeasureToUnitOfMeasureDto;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureDto unitOfMeasureToUnitOfMeasureDto) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureDto = unitOfMeasureToUnitOfMeasureDto;
    }


    @Override
    public Set<UnitOfMeasureDto> listAllUoms() {
       return StreamSupport
               .stream(unitOfMeasureRepository.findAll()
               .spliterator(), false)
               .map(unitOfMeasureToUnitOfMeasureDto::convert)
               .collect(Collectors.toSet());

    }
}
