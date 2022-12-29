package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeRepository recipeRepository;

    public DataLoader(CategoryRepository categoryRepository,
                      UnitOfMeasureRepository unitOfMeasureRepository,
                      RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    private UnitOfMeasure findUOMByDescription(String description) {
        return unitOfMeasureRepository.findByDescription(description)
                .orElseThrow(() -> new RuntimeException("Expected Unit of Measure Not Found"));
    }

    private Category findCategoryByDescription(String description) {
        return categoryRepository.findByDescription(description)
                .orElseThrow(() -> new RuntimeException("Expected Category Not Found"));
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipeList = new ArrayList<>();

        Recipe guacamoleRecipe = new Recipe();
        guacamoleRecipe.setDescription("How to Make the Best Guacamole");
        guacamoleRecipe.setPrepTime(10);
        guacamoleRecipe.setCookTime(10);
        guacamoleRecipe.setServings(4);
        guacamoleRecipe.setSource("Simply Recipes");
        guacamoleRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamoleRecipe.setDirections("1. Cut the avocado: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl. \n2. Mash the avocado flesh: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.) \n3. Add the remaining ingredients to taste: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown. Add the chopped onion, cilantro, black pepper, and chilis. Chili peppers vary individually in their spiciness. So, start with a half of one chili pepper and add more to the guacamole to your desired degree of heat. Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste. \n4. Serve immediately: If making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Garnish with slices of red radish or jigama strips. Serve with your choice of store-bought tortilla chips or make your own homemade tortilla chips. Refrigerate leftover guacamole up to 3 days. Note: Chilling tomatoes hurts their flavor. So, if you want to add chopped tomato to your guacamole, add it just before serving.");
        guacamoleRecipe.setImage(new Byte[]{1, 2, 3});
        guacamoleRecipe.setNotes(new Notes("Ingredients for Easy Guacamole All you really need to make guacamole is ripe avocados and salt. After that, a little lime or lemon juice—a splash of acidity—will help balance the richness of the avocado. If you want, add chopped cilantro, chilis, onion, and/or tomato. How To Pick Perfectly Ripe Avocados The trick to making perfect guacamole is using avocados that are just the right amount of ripeness. Not ripe enough and the avocado will be hard and flavorless. Too ripe and the taste will be off.Check for ripeness by gently pressing the outside of the avocado. If there is no give, the avocado is not ripe yet. If there is a little give, the avocado is ripe. If there is a lot of give, the avocado may be too ripe and not good. In this case, taste test first before using.  How To Cut an Avocado To slice open an avocado, cut it in half lengthwise with a sharp chef's knife and twist apart. One side will have the pit. To remove it, you can carefully tap your chef’s knife against the pit and twist to dislodge it (protecting your hand with a towel), or you can cut the avocado into quarters and remove the pit with your fingers or a spoon. Other Ways To Use Guacamole  Guacamole has a role in the kitchen beyond a party dip. It's great scooped on top of nachos and also makes an excellent topping or side for enchiladas, tacos, grilled salmon, or oven-baked chicken. Guacamole is great in foods, as well. Try mixing some into a tuna sandwich or your next batch of deviled eggs. How To Store Guacamole Guacamole is best eaten right after it's made. Like apples, avocados start to oxidize and turn brown once they've been cut. That said, the acid in the lime juice you add to guacamole can help slow down that process. And if you store the guacamole properly, you can easily make it a few hours ahead if you are preparing for a party. The trick to keeping guacamole green is to make sure air doesn't touch it! Transfer it to a container, cover with plastic wrap, and press down on the plastic wrap to squeeze out any air pockets. Make sure any exposed surface of the guacamole is touching the plastic wrap, not air. This will keep the amount of browning to a minimum. You can store the guacamole in the fridge this way for up to three days. If the guacamole develops discoloration, you can either scrape off the brown parts and discard, or stir into the rest of the guacamole before serving."));
        guacamoleRecipe.setDifficulty(Difficulty.EASY);
        guacamoleRecipe.setCategories(Set.of(findCategoryByDescription("Mexican")));
        guacamoleRecipe.addIngredient(new Ingredient("Avocado", BigDecimal.valueOf(2), findUOMByDescription("Unit")));
        guacamoleRecipe.addIngredient(new Ingredient("Salt", BigDecimal.valueOf(0.4), findUOMByDescription("Teaspoon")));
        guacamoleRecipe.addIngredient(new Ingredient("Lemon juice", BigDecimal.valueOf(1), findUOMByDescription("Tablespoon")));
        guacamoleRecipe.addIngredient(new Ingredient("Onion", BigDecimal.valueOf(4), findUOMByDescription("Tablespoon")));
        guacamoleRecipe.addIngredient(new Ingredient("Serrano", BigDecimal.valueOf(2), findUOMByDescription("Unit")));
        guacamoleRecipe.addIngredient(new Ingredient("Cilantro", BigDecimal.valueOf(2), findUOMByDescription("Tablespoon")));
        guacamoleRecipe.addIngredient(new Ingredient("Pepper", BigDecimal.valueOf(1), findUOMByDescription("Pinch")));
        guacamoleRecipe.addIngredient(new Ingredient("Tortilla chips", BigDecimal.valueOf(1), findUOMByDescription("Unit")));
        recipeList.add(guacamoleRecipe);

        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Tacos");
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setCookTime(15);
        tacosRecipe.setServings(6);
        tacosRecipe.setSource("Simply Recipes");
        tacosRecipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        tacosRecipe.setDirections("1. Prepare the grill: Prepare either a gas or charcoal grill for medium-high, direct heat. \n2. Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over. Set aside to marinate while the grill heats and you prepare the rest of the toppings. \n3. Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165°F. Transfer to a plate and rest for 5 minutes. \n4. Thin the sour cream with milk: Stir together the sour cream and milk to thin out the sour cream to make it easy to drizzle. \n5. Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges. \n6. Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side. Wrap warmed tortillas in a tea towel to keep them warm until serving.");
        tacosRecipe.setImage(new Byte[]{4, 5, 6});
        tacosRecipe.setNotes(new Notes("Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house. Today's tacos are more purposeful a deliberate meal instead of a secretive midnight snack! First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings. Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!  The ancho chiles I use in the marinade are named for their wide shape. They are large, have a deep reddish brown color when dried, and are mild in flavor with just a hint of heat. You can find ancho chile powder at any markets that sell Mexican ingredients, or online. I like to put all the toppings in little bowls on a big platter at the center of the table: avocados, radishes, tomatoes, red onions, wedges of lime, and a sour cream sauce. I add arugula, as well—this green isn't traditional for tacos, but we always seem to have some in the fridge and I think it adds a nice green crunch to the tacos. Everyone can grab a warm tortilla from the pile and make their own tacos just they way they like them. You could also easily double or even triple this recipe for a larger party. A taco and a cold beer on a warm day? Now that's living!"));
        tacosRecipe.setDifficulty(Difficulty.MODERATE);
        tacosRecipe.setCategories(Set.of(findCategoryByDescription("Mexican"), findCategoryByDescription("Fast Food")));
        tacosRecipe.addIngredient(new Ingredient("Chili powder", BigDecimal.valueOf(2), findUOMByDescription("Tablespoon")));
        tacosRecipe.addIngredient(new Ingredient("Oregano", BigDecimal.valueOf(1), findUOMByDescription("Teaspoon")));
        tacosRecipe.addIngredient(new Ingredient("Cumin", BigDecimal.valueOf(1), findUOMByDescription("Teaspoon")));
        tacosRecipe.addIngredient(new Ingredient("Sugar", BigDecimal.valueOf(1), findUOMByDescription("Teaspoon")));
        tacosRecipe.addIngredient(new Ingredient("Kosher salt", BigDecimal.valueOf(0.5), findUOMByDescription("Teaspoon")));
        tacosRecipe.addIngredient(new Ingredient("Garlic", BigDecimal.valueOf(1), findUOMByDescription("Сlove")));
        tacosRecipe.addIngredient(new Ingredient("Orange zest", BigDecimal.valueOf(1), findUOMByDescription("Tablespoon")));
        tacosRecipe.addIngredient(new Ingredient("Orange juice", BigDecimal.valueOf(3), findUOMByDescription("Tablespoon")));
        tacosRecipe.addIngredient(new Ingredient("Olive oil", BigDecimal.valueOf(2), findUOMByDescription("Tablespoon")));
        tacosRecipe.addIngredient(new Ingredient("Chicken thighs", BigDecimal.valueOf(1), findUOMByDescription("Pound")));
        tacosRecipe.addIngredient(new Ingredient("Corn tortillas", BigDecimal.valueOf(8), findUOMByDescription("Unit")));
        tacosRecipe.addIngredient(new Ingredient("Arugula", BigDecimal.valueOf(3), findUOMByDescription("Cup")));
        tacosRecipe.addIngredient(new Ingredient("Ripe avocado", BigDecimal.valueOf(2), findUOMByDescription("Unit")));
        tacosRecipe.addIngredient(new Ingredient("Radish", BigDecimal.valueOf(4), findUOMByDescription("Unit")));
        tacosRecipe.addIngredient(new Ingredient("Cherry tomatoes", BigDecimal.valueOf(0.5), findUOMByDescription("Pint")));
        tacosRecipe.addIngredient(new Ingredient("Sliced onion", BigDecimal.valueOf(0.25), findUOMByDescription("Unit")));
        tacosRecipe.addIngredient(new Ingredient("Chopped cilantro", BigDecimal.valueOf(1), findUOMByDescription("Unit")));
        tacosRecipe.addIngredient(new Ingredient("Sour cream", BigDecimal.valueOf(0.5), findUOMByDescription("Cup")));
        tacosRecipe.addIngredient(new Ingredient("Milk", BigDecimal.valueOf(0.5), findUOMByDescription("Cup")));
        tacosRecipe.addIngredient(new Ingredient("Lime", BigDecimal.valueOf(1), findUOMByDescription("Unit")));
        recipeList.add(tacosRecipe);

        return recipeList;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
        log.debug("Loading Bootstrap Data");
    }
}
