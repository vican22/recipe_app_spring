package com.vedran.recipe.services;

import com.vedran.recipe.converters.IngredientDtoToIngredient;
import com.vedran.recipe.converters.IngredientToIngredientDto;
import com.vedran.recipe.dto.IngredientDto;
import com.vedran.recipe.models.Ingredient;
import com.vedran.recipe.models.Recipe;
import com.vedran.recipe.repositories.RecipeRepository;
import com.vedran.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientDto convertIngredient;
    private final IngredientDtoToIngredient convertIngredientDto;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientToIngredientDto convertIngredient,
                                 IngredientDtoToIngredient covertIngredientDto,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.convertIngredient = convertIngredient;
        this.convertIngredientDto = covertIngredientDto;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientDto findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe id not found: " + recipeId));

        return recipe.getIngredients()
                 .stream()
                 .filter(ingredient -> ingredient.getId().equals(ingredientId))
                 .map(convertIngredient::convert)
                 .findFirst()
                 .orElseThrow(() -> new RuntimeException("Ingredient id not found: " + ingredientId));
    }

    @Override
    @Transactional
    public IngredientDto saveIngredientDto(IngredientDto ingredientDto) {
        Recipe recipe = recipeRepository.findById(ingredientDto.getRecipeId())
                .orElseThrow(() -> new RuntimeException("Recipe not found id: " + ingredientDto.getId()));

        Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientDto.getId()))
                .findFirst();

        if (ingredientOptional.isPresent()) {
            Ingredient ingredientFound = ingredientOptional.get();
            ingredientFound.setDescription(ingredientDto.getDescription());
            ingredientFound.setAmount(ingredientDto.getAmount());

            log.info("UOM: " + ingredientDto.getUnitOfMeasure().getId());
            ingredientFound
                    .setUnitOfMeasure(
                            unitOfMeasureRepository
                                    .findById(
                                            ingredientDto.getUnitOfMeasure().getId())
            .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
        } else {
            recipe.addIngredient(convertIngredientDto.convert(ingredientDto));
        }

        Recipe savedRecipe = recipeRepository.save(recipe);


        return convertIngredient.convert(savedRecipe.getIngredients().stream()
        .filter(ingredient -> ingredient.getId().equals(ingredientDto.getId()))
                .findFirst().get());
    }
}
