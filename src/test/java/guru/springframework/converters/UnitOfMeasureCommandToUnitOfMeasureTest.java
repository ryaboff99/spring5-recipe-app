package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("UnitOfMeasureCommand to UnitOfMeasure conversion tests")
class UnitOfMeasureCommandToUnitOfMeasureTest {

    public static final String DESCRIPTION  = "description";
    public static final Long LONG_VALUE = 1L;

    UnitOfMeasureCommandToUnitOfMeasure converter;

    @BeforeEach
    void setUp() throws Exception {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    @DisplayName("Convert null UnitOfMeasureCommand to null UnitOfMeasure")
    public void convertNullUnitOfMeasureCommandToNullUnitOfMeasure() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    @DisplayName("Convert empty UnitOfMeasureCommand to empty UnitOfMeasure")
    public void convertEmptyUnitOfMeasureCommandToEmptyUnitOfMeasure() throws Exception {
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    @DisplayName("Convert UnitOfMeasureCommand to UnitOfMeasure")
    public void convertUnitOfMeasureCommandToUnitOfMeasure() throws Exception {
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(LONG_VALUE);
        command.setDescription(DESCRIPTION);

        UnitOfMeasure uom = converter.convert(command);

        assertNotNull(uom);
        assertEquals(LONG_VALUE, uom.getId());
        assertEquals(DESCRIPTION, uom.getDescription());
    }
}