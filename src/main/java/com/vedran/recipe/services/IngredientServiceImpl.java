package com.vedran.recipe.services;

import com.vedran.recipe.converters.IngredientToIngredientDto;
import com.vedran.recipe.dto.IngredientDto;
import com.vedran.recipe.models.Recipe;
import com.vedran.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientDto convertIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientDto convertIngredient) {
        this.recipeRepository = recipeRepository;
        this.convertIngredient = convertIngredient;
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
}
