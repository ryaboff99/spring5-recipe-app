package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Notes to NotesCommand conversion tests")
class NotesToNotesCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final String RECIPE_NOTES = "Notes";
    NotesToNotesCommand converter;

    @BeforeEach
    void setUp() throws Exception{
        converter = new NotesToNotesCommand();
    }

    @Test
    @DisplayName("Convert Notes to NotesCommand")
    void convertNotesToNotesCommand() throws Exception {
        Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setRecipeNotes(RECIPE_NOTES);

        NotesCommand notesCommand = converter.convert(notes);

        assertEquals(ID_VALUE, notesCommand.getId());
        assertEquals(RECIPE_NOTES, notesCommand.getRecipeNotes());
    }

    @Test
    @DisplayName("Convert null Notes to null NotesCommand")
    public void convertNullNotesToNullNotesCommand() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    @DisplayName("Convert empty Notes to empty NotesCommand")
    public void ConvertEmptyNotesToEmptyNotesCommand() throws Exception {
        assertNotNull(converter.convert(new Notes()));
    }
}