package com.vedran.recipe.converters;

import com.vedran.recipe.dto.NotesDto;
import com.vedran.recipe.models.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesToNotesDtoTest {

    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";

    NotesToNotesDto converter;

    @BeforeEach
    void setUp() {
        converter = new NotesToNotesDto();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Notes()));
    }

    @Test
    void convert() {
        //given
        Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setRecipeNotes(DESCRIPTION);

        //when
        NotesDto dto = converter.convert(notes);

        //then
        assertNotNull(dto);
        assertEquals(ID_VALUE, dto.getId());
        assertEquals(DESCRIPTION, dto.getRecipeNotes());
    }
}