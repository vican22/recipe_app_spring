package com.vedran.recipe.dto;

import com.vedran.recipe.models.Difficulty;
import com.vedran.recipe.models.Ingredient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeDto {
    private Long id;
    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @Min(1)
    @Max(999)
    private Integer prepTime;

    @Min(1)
    @Max(999)
    private Integer cookTime;

    @Min(1)
    @Max(100)
    private Integer servings;

    @URL
    private String url;

    @NotBlank
    private String directions;
    private String source;
    private Byte[] image;
    private Set<IngredientDto> ingredients = new HashSet<>();
    private Difficulty difficulty;
    private NotesDto notes;
    private Set<CategoryDto> categories = new HashSet<>();
}
