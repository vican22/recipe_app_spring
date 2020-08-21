package com.vedran.recipe.services;

import com.vedran.recipe.converters.RecipeDtoToRecipe;
import com.vedran.recipe.converters.RecipeToRecipeDto;
import com.vedran.recipe.dto.RecipeDto;
import com.vedran.recipe.exceptions.NotFoundException;
import com.vedran.recipe.models.Recipe;
import com.vedran.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeDtoToRecipe dtoToRecipeConverter;
    private final RecipeToRecipeDto recipeToRecipeDtoConverter;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeDtoToRecipe dtoToRecipeConverter, RecipeToRecipeDto recipeToRecipeDtoConverter) {
        this.recipeRepository = recipeRepository;
        this.dtoToRecipeConverter = dtoToRecipeConverter;
        this.recipeToRecipeDtoConverter = recipeToRecipeDtoConverter;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipes = new HashSet<>();

         recipeRepository
                 .findAll()
                 .iterator()
                 .forEachRemaining(recipes::add);

         return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe Not Found. For id value: " + id));
    }

    @Override
    @Transactional
    public RecipeDto saveRecipeDto(RecipeDto recipeDto) {
        Recipe detachedRecipe = dtoToRecipeConverter.convert(recipeDto);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.info("Saved RecipeId: " + savedRecipe.getId());
        return recipeToRecipeDtoConverter.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeDto findRecipeDtoById(Long id) {
        return recipeToRecipeDtoConverter.convert(findById(id));
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
