package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("CategoryCommand to Category conversion tests")
class CategoryCommandToCategoryTest {

    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";
    CategoryCommandToCategory converter;

    @BeforeEach
    public void setUp() throws Exception {
        converter = new CategoryCommandToCategory();
    }

    @Test
    @DisplayName("Convert null CategoryCommand to null Category")
    public void convertNullCategoryCommandToNullCategory() throws Exception { // TODO: delete unused exceptions?
        assertNull(converter.convert(null));
    }

    @Test
    @DisplayName("Convert empty CategoryCommand to empty Category")
    public void convertEmptyCategoryCommandToEmptyCategory() throws Exception {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    @DisplayName("Convert CategoryCommand to Category")
    void convertCategoryCommandToCategory() throws Exception {
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID_VALUE);
        categoryCommand.setDescription(DESCRIPTION);

        Category category = converter.convert(categoryCommand);

        assertEquals(ID_VALUE, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }
}