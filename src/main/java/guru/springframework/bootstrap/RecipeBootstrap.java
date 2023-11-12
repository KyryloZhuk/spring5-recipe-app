package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repository.CategoryRepository;
import guru.springframework.repository.RecipeRepository;
import guru.springframework.repository.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(createRecipes());
    }

    private List<Recipe> createRecipes(){
        List<Recipe> recipes = new ArrayList<>();
        List<UnitOfMeasure> unitsOfMeasureList = (List<UnitOfMeasure>) unitOfMeasureRepository.findAll();
        List<Category> categoriesList = (List<Category>) categoryRepository.findAll();
        if (unitsOfMeasureList.isEmpty() || categoriesList.isEmpty()){
            throw new RuntimeException("List not found");
        }
        Map<String, UnitOfMeasure> unitsOfMeasure = unitsOfMeasureList.stream().collect(Collectors.toMap(UnitOfMeasure::getDescription, obj -> obj));
        Map<String, Category> categories = categoriesList.stream().collect(Collectors.toMap(Category::getDescription, obj -> obj));


        Recipe mapoTofu = new Recipe();
        mapoTofu.setDescription("Traditional seczuanese mapo tofu");
        mapoTofu.setPrepTime(15);
        mapoTofu.setCookTime(30);
        mapoTofu.setDifficulty(Difficulty.MODERATE);
        mapoTofu.setDirections("Cut the tofu into 2cm cubes and leave to steep in very hot, lightly salted water while you prepare the other ingredients.\n" +
                "Cut the spring onions or green garlic into 2cm lengths.\n" +
                "Heat a seasoned wok over a high flame. Pour in 1 tbsp cooking oil and heat until the sides of the wok have begun to smoke.\n" +
                "Add the beef and stir-fry until it is fully cooked and fragrant, breaking the clumps of meat into tiny pieces as you go. Remove from the wok with a slotted spoon and set aside.\n" +
                "Rinse and dry the wok if necessary, then re-season it and return to a medium flame. Pour in 5 tbsp cooking oil and swirl it around. Add the chilli bean paste and stir-fry until the oil is a rich red colour and smells delicious. Next add the black beans and ground chillies and stir-fry for a few seconds more until you can smell them too, then do the same with the garlic and ginger.\n" +
                "Take care not to overheat the aromatics - you want to end up with a thick, fragrant sauce, and the secret is to let them sizzle gently, allowing the oil to coax out their flavours.\n" +
                "Remove the tofu from the hot water with a perforated ladle, shaking off any excess liquid, and lay it gently into the wok.\n" +
                "Sprinkle over the beef, then add the stock or water and white pepper. Nudge the tofu tenderly into the sauce with the back of your ladle or wok scoop to avoid breaking up the cubes.\n" +
                "Bring to the boil, then simmer for a couple of minutes to allow the tofu to absorb the flavours of the seasonings. If you're using green garlic (or baby leeks or garlic sprouts), stir them in now.");
        Notes mapoTofuNotes = new Notes();
        mapoTofuNotes.setRecipeNotes("This dish is most delicious when made with mature Pixian chilli bean paste, with its deep chestnut colour and ripe savoury flavour. Adjust the final sprinkling of Sichuan pepper according to your guests\' tastes (Sichuanese people can take about four times as much pepper as outsiders, in my experience).");
        mapoTofu.setNotes(mapoTofuNotes);

        mapoTofu.addIngredient(new Ingredient("Plain white tofu", BigDecimal.valueOf(500), unitsOfMeasure.get("Gram")));
        mapoTofu.addIngredient(new Ingredient("Spring onion", BigDecimal.valueOf(2), unitsOfMeasure.get("Unit")));
        mapoTofu.addIngredient(new Ingredient("Cooking oil", BigDecimal.valueOf(6), unitsOfMeasure.get("Tablespoon")));
        mapoTofu.addIngredient(new Ingredient("Minced beef", BigDecimal.valueOf(100), unitsOfMeasure.get("Gram")));
        mapoTofu.addIngredient(new Ingredient("Sichuan chilli bean paste", BigDecimal.valueOf(2.5), unitsOfMeasure.get("Tablespoon")));
        mapoTofu.addIngredient(new Ingredient("Fermented black beans", BigDecimal.valueOf(1), unitsOfMeasure.get("Tablespoon")));
        mapoTofu.addIngredient(new Ingredient("Ground chilies", BigDecimal.valueOf(2), unitsOfMeasure.get("Teaspoon")));
        mapoTofu.addIngredient(new Ingredient("Finely chopped garlic", BigDecimal.valueOf(1), unitsOfMeasure.get("Tablespoon")));
        mapoTofu.addIngredient(new Ingredient("Finely chopped ginger", BigDecimal.valueOf(1), unitsOfMeasure.get("Tablespoon")));
        mapoTofu.addIngredient(new Ingredient("Stock or water", BigDecimal.valueOf(175), unitsOfMeasure.get("Milliliter")));
        mapoTofu.addIngredient(new Ingredient("Ground white pepper", BigDecimal.valueOf(0.25), unitsOfMeasure.get("Teaspoon")));
        mapoTofu.addIngredient(new Ingredient("Potato starch", BigDecimal.valueOf(1), unitsOfMeasure.get("Tablespoon")));
        mapoTofu.addIngredient(new Ingredient("Cold water", BigDecimal.valueOf(2.5), unitsOfMeasure.get("Tablespoon")));
        mapoTofu.addIngredient(new Ingredient("Roasted sichuan pepper", BigDecimal.valueOf(1), unitsOfMeasure.get("Teaspoon")));

        mapoTofu.getCategories().add(categories.get("Chinese"));
        mapoTofu.getCategories().add(categories.get("Sichuan"));

        mapoTofu.setUrl("nourl");
        mapoTofu.setServings(2);
        mapoTofu.setSource("The Food Of Sichuan");

        recipes.add(mapoTofu);

        Recipe solyanka = new Recipe();
        solyanka.setDescription("Solyanka. As meaty as it gets");
        solyanka.setPrepTime(25);
        solyanka.setCookTime(60);
        solyanka.setDifficulty(Difficulty.EASY);
        solyanka.setDirections("First cook the bouillon. Cut onion and carrots into small pieces, give a quick fry, put into a pot. Then cut (optional) and briefly fry pickles, add to the pot. Next, add all meats.\n" +
                "Put tomato paste into the pan, fry for a minute, into the pot. Fill the pot with bouillon. Add olives, salt, pepper. Cook for 30-40 minutes (or less). Add lemon when ready.");
        Notes solyankaNotes = new Notes();
        solyankaNotes.setRecipeNotes("Best served with sour cream and lemon");
        solyanka.setNotes(solyankaNotes);

        solyanka.addIngredient(new Ingredient("Onions", BigDecimal.valueOf(1), unitsOfMeasure.get("Unit")));
        solyanka.addIngredient(new Ingredient("Carrot", BigDecimal.valueOf(1), unitsOfMeasure.get("Unit")));
        solyanka.addIngredient(new Ingredient("Stock", BigDecimal.valueOf(1), unitsOfMeasure.get("Liter")));
        solyanka.addIngredient(new Ingredient("Pickles", BigDecimal.valueOf(150), unitsOfMeasure.get("Gram")));
        solyanka.addIngredient(new Ingredient("Smoked ribs", BigDecimal.valueOf(150), unitsOfMeasure.get("Gram")));
        solyanka.addIngredient(new Ingredient("Kabanos", BigDecimal.valueOf(100), unitsOfMeasure.get("Gram")));
        solyanka.addIngredient(new Ingredient("Cold smoked sausage", BigDecimal.valueOf(150), unitsOfMeasure.get("Gram")));
        solyanka.addIngredient(new Ingredient("Roasted pork", BigDecimal.valueOf(150), unitsOfMeasure.get("Gram")));
        solyanka.addIngredient(new Ingredient("Roasted pork", BigDecimal.valueOf(150), unitsOfMeasure.get("Gram")));
        solyanka.addIngredient(new Ingredient("Jerky", BigDecimal.valueOf(60), unitsOfMeasure.get("Gram")));
        solyanka.addIngredient(new Ingredient("Tomato paste", BigDecimal.valueOf(100), unitsOfMeasure.get("Gram")));
        solyanka.addIngredient(new Ingredient("Black olives", BigDecimal.valueOf(25), unitsOfMeasure.get("Gram")));
        solyanka.addIngredient(new Ingredient("Lemon", BigDecimal.valueOf(1), unitsOfMeasure.get("Unit")));

        solyanka.getCategories().add(categories.get("Ukrainian"));

        solyanka.setUrl("nourl");
        solyanka.setServings(6);
        solyanka.setSource("Word of mouth");

        recipes.add(solyanka);

        return recipes;
    }


}
