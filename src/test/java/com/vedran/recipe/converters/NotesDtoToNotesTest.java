package com.vedran.recipe.converters;

import com.vedran.recipe.dto.NotesDto;
import com.vedran.recipe.models.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesDtoToNotesTest {

    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";

    NotesDtoToNotes converter;

    @BeforeEach
    void setUp() {
        converter = new NotesDtoToNotes();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new NotesDto()));
    }

    @Test
    void convert() {
        //given
        NotesDto dto = new NotesDto();
        dto.setId(ID_VALUE);
        dto.setRecipeNotes(DESCRIPTION);

        //when
        Notes notes = converter.convert(dto);

        //then
        assertNotNull(notes);
        assertEquals(ID_VALUE, notes.getId());
        assertEquals(DESCRIPTION, notes.getRecipeNotes());

    }
}