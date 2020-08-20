package com.vedran.recipe.services;

import com.vedran.recipe.converters.UnitOfMeasureToUnitOfMeasureDto;
import com.vedran.recipe.dto.UnitOfMeasureDto;
import com.vedran.recipe.models.UnitOfMeasure;
import com.vedran.recipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {

    UnitOfMeasureToUnitOfMeasureDto unitOfMeasureToUnitOfMeasureDto = new UnitOfMeasureToUnitOfMeasureDto();
    UnitOfMeasureService unitOfMeasureService;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations
                .initMocks(this);

        unitOfMeasureService = new UnitOfMeasureServiceImpl(
                unitOfMeasureRepository,
                unitOfMeasureToUnitOfMeasureDto);
    }

    @Test
    void listAllUoms() {
        //given
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);
        unitOfMeasures.add(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);
        unitOfMeasures.add(uom2);

        when(unitOfMeasureRepository.findAll())
                .thenReturn(unitOfMeasures);

        //when
        Set<UnitOfMeasureDto> unitOfMeasureDtos = unitOfMeasureService.listAllUoms();

        //then
        assertEquals(2, unitOfMeasureDtos.size());
        verify(unitOfMeasureRepository, times(1)).findAll();
    }
}