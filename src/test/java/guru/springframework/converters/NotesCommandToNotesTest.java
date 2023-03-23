package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("NotesCommand to Notes conversion tests")
class NotesCommandToNotesTest {

    public static final Long ID_VALUE = 1L;
    public static final String RECIPE_NOTES = "Notes";
    NotesCommandToNotes converter;

    @BeforeEach
    void setUp() throws Exception {
        converter = new NotesCommandToNotes();
    }

    @Test
    @DisplayName("Convert null NotesCommand to null Notes")
    public void convertNullNotesCommandToNullNotes() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    @DisplayName("Convert empty NotesCommand to empty Notes")
    public void convertEmptyNotesCommandToEmptyNotes() throws Exception {
        assertNotNull(converter.convert(new NotesCommand()));
    }

    @Test
    @DisplayName("Convert NotesCommand to Notes")
    public void convert() throws Exception {
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID_VALUE);
        notesCommand.setRecipeNotes(RECIPE_NOTES);

        Notes notes = converter.convert(notesCommand);

        assertNotNull(notes);
        assertEquals(ID_VALUE, notes.getId());
        assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
    }
}