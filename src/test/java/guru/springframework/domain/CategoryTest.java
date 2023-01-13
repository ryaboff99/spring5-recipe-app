package guru.springframework.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    Category category;

    @BeforeEach
    void getCategory() {
        category = new Category();
    }

    @DisplayName("Id should be 4")
    @Test
    void getId() {
        Long idValue = 4L;

        category.setId(idValue);

        assertEquals(category.getId(), idValue);
    }

    @Test
    void getDescription() {
    }

    @Test
    void getRecipes() {
    }
}