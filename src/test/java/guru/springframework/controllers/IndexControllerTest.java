package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class IndexControllerTest {
    @Mock
    RecipeService recipeService;
    @Mock
    Model model;

    IndexController controller;

    @BeforeEach
    void setUp() {
        recipeService = mock(RecipeService.class);
        model = mock(Model.class);
        controller = new IndexController(recipeService);
    }

    @Test
    void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/")) // path
                .andExpect(status().isOk()) // status to return from request call
                .andExpect(view().name("index")); // view name - return result from request call
    }

    @Test
    void listRecipes() {
        // given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());

        Recipe recipe = new Recipe(); // we need to define new recipe and add some value to it, coz if they are all default (all with null values) they are technically equal and so second replace first and not add it to the Set
        recipe.setId(1L);
        recipes.add(recipe);

        Set<Recipe> recipes1 = new HashSet<>();
        recipes1.add(new Recipe());

        when(recipeService.getRecipes()).thenReturn(recipes); // argumentCaptor receives what is passed here

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        // when
        String viewName = controller.listRecipes(model);

        // then
        assertEquals(viewName, "index");
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture()); // calls Mockito.verify() to verify if the .addAttribute() method of the mocked 'model' has been called. Then the call to captor.capture() captures the method argument ('recipes' in curr. ex.) passed to the mock method. To capture the method arguments, you need to use the capture() method of ArgumentCaptor. You should call it during the verification phase of the test.
        // verify(model, times(1)).addAttribute(eq("recipes"), recipeService.getRecipes()); // such variant (without argumentCaptor.capture() will trigger InvalidUseOfMatchersException because if one argument is matcher, other arguments must be matchers too and not raw types (such as raw String "recipes" or common value returned from method recipeService.getRecipes())

        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size()); // assertion by calling the getValue() method of ArgumentCaptor to get the captured value of the argument

        // Note: If the verified methods are called multiple times, then the getValue() method will return the latest captured value.
    }

    @Test
    void listRecipes1() {
        // given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());

        Recipe recipe = new Recipe(); // we need to define new recipe and add some value to it, coz if they are all default (all with null values) they are technically equal and so second replace first and not add it to the Set
        recipe.setId(1L);
        recipes.add(recipe);

        Set<Recipe> recipes1 = new HashSet<>();
        recipes1.add(new Recipe());

        when(recipeService.getRecipes()).thenReturn(recipes);

        // when
        String viewName = controller.listRecipes(model);

        // then
        assertEquals(viewName, "index");
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute("recipes", recipeService.getRecipes()); // such variant (without argumentCaptor.capture() will trigger InvalidUseOfMatchersException because in verify() only matchers are allowed and not raw types (such as raw String or common value returned from method)

        assertEquals(2, recipeService.getRecipes().size());
    }
}