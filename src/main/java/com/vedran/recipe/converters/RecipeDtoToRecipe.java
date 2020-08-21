package com.vedran.recipe.converters;

import com.vedran.recipe.dto.RecipeDto;
import com.vedran.recipe.models.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeDtoToRecipe implements Converter<RecipeDto, Recipe> {

    private final CategoryDtoToCategory categoryConverter;
    private final IngredientDtoToIngredient ingredientConverter;
    private final NotesDtoToNotes notesConverter;

    public RecipeDtoToRecipe(CategoryDtoToCategory categoryConverter,
                             IngredientDtoToIngredient ingredientConverter,
                             NotesDtoToNotes notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeDto source) {
        if (source == null) {
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setCookTime(source.getCookTime());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setDescription(source.getDescription());
        recipe.setDirections(source.getDirections());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setImage(source.getImage());
        recipe.setUrl(source.getUrl());
        recipe.setNotes(notesConverter.convert(source.getNotes()));

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                    .forEach(category -> recipe.getCategories().add(categoryConverter.convert(category)));
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.getIngredients()
                    .forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return recipe;
    }
}
