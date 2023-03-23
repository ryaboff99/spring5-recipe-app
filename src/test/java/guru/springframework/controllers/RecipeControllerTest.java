package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("Recipe Controller Tests")
public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    RecipeController recipeController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        recipeController = new RecipeController(recipeService);

        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Recipe is displayed by id")
    void recipeIsDisplayedById() throws Exception {
        Long id = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeService.findById(id)).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }

    @Test
    @DisplayName("Error 404 is displayed if do not find Recipe by id")
    public void error404IsDisplayedIfDoNotFindRecipeById() throws Exception {
        when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"))
                .andExpect(model().attributeExists("exception"));
    }

    @Test
    @DisplayName("Error 400 is displayed if pass incorrect type to Recipe id")
    public void error400IsDisplayedIfPassIncorrectTypeToRecipeId() throws Exception {
        mockMvc.perform(get("/recipe/abc/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"))
                .andExpect(model().attributeExists("exception"));
    }

    @Test
    @DisplayName("Create new Recipe Form is displayed")
    public void createNewRecipeFormIsDisplayed() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }

    @Test
    @DisplayName("New Recipe from Recipe Form persisted")
    public void newRecipeFromRecipeFormPersisted() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        mockMvc.perform(post("/recipe")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
                        .param("description", "some string")
                        .param("directions", "some directions")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/2/show/"));
    }

    @Test
    @DisplayName("Recipe validation errors are shown")
    public void recipeValidationErrorsAreShown() throws Exception {
        mockMvc.perform(post("/recipe") // call Recipe Controller saveOrUpdate() method that takes updated RecipeCommand from View Form and persist it in DB
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED) // mock POST request method with blank 'directions, and 'description' variables that will trigger validation errors and
                        .param("id", "")
                        .param("cookTime", "3000")
                        .param("url", "sdfs://www.simplyrecipes.com/recipes/perfect_guacamole/")
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/recipeform"));   // expect back Form for update, coz invalid variables defined
    }

    @Test
    @DisplayName("Update Recipe Form is displayed")
    public void updateRecipeFormIsDisplayed() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }

    @Test
    @DisplayName("Recipe is deleted by id")
    public void recipeIsDeletedById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"));

        verify(recipeService, times(1)).deleteById(anyLong());
    }
}