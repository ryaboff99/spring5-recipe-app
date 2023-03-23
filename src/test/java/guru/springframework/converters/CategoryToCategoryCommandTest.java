package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Category to CategoryCommand conversion tests")
class CategoryToCategoryCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "descript";
    CategoryToCategoryCommand converter;

    @BeforeEach
    void setUp() throws Exception {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    @DisplayName("Convert null Category to null CategoryCommand")
    public void convertNullCategoryToNullCategoryCommand() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    @DisplayName("Convert empty Category to empty CategoryCommand")
    public void convertEmptyCategoryToEmptyCategoryCommand() throws Exception {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    @DisplayName("Convert Category to CategoryCommand")
    void convert() throws Exception {
        //given
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        //when
        CategoryCommand categoryCommand = converter.convert(category);

        //then
        assertEquals(ID_VALUE, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());
    }
}