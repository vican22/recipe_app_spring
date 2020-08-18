package com.vedran.recipe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotesDto {
    private Long id;
    private String recipeNotes;
}
