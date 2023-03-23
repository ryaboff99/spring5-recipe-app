package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("UnitOfMeasure to UnitOfMeasureCommand tests")
class UnitOfMeasureToUnitOfMeasureCommandTest {

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = 1L;

    UnitOfMeasureToUnitOfMeasureCommand converter;

    @BeforeEach
    void setUp() throws Exception {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    @DisplayName("Convert null UnitOfMeasure to null UnitOfMeasureCommand")
    public void convertNullUnitOfMeasureToNullUnitOfMeasureCommand() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    @DisplayName("Convert empty UnitOfMeasure to empty UnitOfMeasureCommand")
    public void convertEmptyUnitOfMeasureToEmptyUnitOfMeasureCommand() throws Exception {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    @DisplayName("Convert UnitOfMeasure to UnitOfMeasureCommand")
    public void convertUnitOfMeasureToUnitOfMeasureCommand() throws Exception {
        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(LONG_VALUE);
        uom.setDescription(DESCRIPTION);
        //when
        UnitOfMeasureCommand uomc = converter.convert(uom);

        //then
        assertEquals(LONG_VALUE, uomc.getId());
        assertEquals(DESCRIPTION, uomc.getDescription());
    }
}