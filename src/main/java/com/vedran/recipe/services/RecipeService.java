package com.vedran.recipe.services;

import com.vedran.recipe.dto.RecipeDto;
import com.vedran.recipe.models.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe findById(Long id);
    RecipeDto saveRecipeDto(RecipeDto recipeDto);
    RecipeDto findRecipeDtoById(Long id);
    void deleteById(Long id);
}
