package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }


    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        Recipe recipe = recipeOptional.orElseThrow(() -> new RuntimeException("Recipe id not found. Id: " + recipeId));

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert).findFirst();

        return ingredientCommandOptional.orElseThrow(() -> new RuntimeException("Ingredient id not found: " + ingredientId));
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) { // take edited Ingredient C.O. from View Form
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId()); // a) Get Recipe mapped to Ingredient C.O. from DB (by recipeId of Ingredient C.O.)

        if (!recipeOptional.isPresent()) {    // exception stub

            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe    // b) Get same Ingredient from Recipe as Ingredient C.O.
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {     // c) Update values of Recipe's Ingredient by values from Ingredient C.O. if Recipe have such Ingredient
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());   // assign new Description form C.O.
                ingredientFound.setAmount(command.getAmount());     // assign new Amount from C.O.
                ingredientFound.setUom(unitOfMeasureRepository      // assign new UOM form C.O.
                        .findById(command.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                Ingredient ingredient = ingredientCommandToIngredient.convert(command); // c) if Recipe don't have such Ingredient we assign Ingredient C.O. to Recipe
                ingredient.setRecipe(recipe);   // assign current Recipe to new Ingredient
                recipe.addIngredient(ingredient);   // assign new Ingredient to current Recipe
            }

            Recipe savedRecipe = recipeRepository.save(recipe); // d) Persist Recipe with Ingredient (whose values are updated) in DB

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()  // again find Ingredient from updated Recipe (with new or updated Ingredient) by id value
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();

            //check by description
            if (!savedIngredientOptional.isPresent()) {
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream() // if Ingredient not found by id value, find Ingredient that best match by all values with Ingredient C.O.
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals((command.getUom().getId())))
                        .findFirst();
            }

            //todo check for fail
            return ingredientToIngredientCommand.convert(savedIngredientOptional.get()); // e) Return Recipe's Ingredient with updated values as Ingredient C.O.
        }
    }

    @Override
    public void deleteIngredientById(Long recipeId, Long ingredientId) {
        log.debug("Deleting Ingredient: " + recipeId + ": " + ingredientId);

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found by id: " + recipeId));

        Optional<Ingredient> ingredientOptional = recipe
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findFirst();

        Ingredient ingredientToRemove = ingredientOptional
                .orElseThrow(() -> new RuntimeException("Ingredient not found by id: " + ingredientId));
        ingredientToRemove.setRecipe(null);

        recipe.getIngredients().remove(ingredientToRemove);

        recipeRepository.save(recipe);
    }
}
