package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeService recipeService;
    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        recipeRepository = mock(RecipeRepository.class);
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    void getRecipeById() {
        Long id = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(id);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(id)).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(id);

        assertEquals(id, recipeReturned.getId());

        verify(recipeRepository).findById(1L);
        verify(recipeRepository, never()).findAll();
    }

    @Test
    void getRecipes() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipeData = new HashSet<>();
        recipeData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipeData);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(1, recipes.size());

        verify(recipeRepository, times(1)).findAll();
    }
}