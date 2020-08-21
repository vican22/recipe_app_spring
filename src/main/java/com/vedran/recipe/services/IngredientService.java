package com.vedran.recipe.services;

import com.vedran.recipe.dto.IngredientDto;

public interface IngredientService {
    IngredientDto findByRecipeIdAndIngredientId(Long recipeId, Long id);
    IngredientDto saveIngredientDto(IngredientDto ingredientDto);
    void deleteById(Long recipeId, Long idToDelete);
}
