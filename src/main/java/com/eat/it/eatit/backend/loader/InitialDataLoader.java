package com.eat.it.eatit.backend.loader;

import com.eat.it.eatit.backend.data.*;
import com.eat.it.eatit.backend.data.refactored.recipe.RecipeComponent;
import com.eat.it.eatit.backend.data.refactored.recipe.RecipeIngredient;
import com.eat.it.eatit.backend.data.refactored.recipe.RecipeRefactored;
import com.eat.it.eatit.backend.data.refactored.recipe.RecipeStep;
import com.eat.it.eatit.backend.enums.ItemType;
import com.eat.it.eatit.backend.mapper.refactored.recipe.RecipePartMapper;
import com.eat.it.eatit.backend.repository.*;
import com.eat.it.eatit.backend.enums.AccountRole;
import com.eat.it.eatit.backend.repository.recipe.RecipeComponentRepository;
import com.eat.it.eatit.backend.repository.recipe.RecipeIngredientRepository;
import com.eat.it.eatit.backend.repository.recipe.RecipeRefactoredRepository;
import com.eat.it.eatit.backend.repository.recipe.RecipeStepRepository;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@Profile("loadInitialData")
@Slf4j
@ToString
class InitialDataLoader {

    private final AccountRepository accountRepository;
    private final ItemRepository itemRepository;
    private final RecipeRepository recipeRepository;
    private final FridgeRepository fridgeRepository;
    private final CookwareRepository cookwareRepository;
    private final ItemInFridgeRepository itemInFridgeRepository;
    private final ItemInRecipeRepository itemInRecipeRepository;

    private final RecipeComponentRepository recipeComponentRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeRefactoredRepository recipeRefactoredRepository;
    private final RecipeStepRepository recipeStepRepository;
    private final PasswordEncoder passwordEncoder;
    private final Random random = new Random();

    @Autowired
    public InitialDataLoader(AccountRepository accountRepository,
                             ItemRepository itemRepository,
                             RecipeRepository recipeRepository,
                             FridgeRepository fridgeRepository,
                             CookwareRepository cookwareRepository,
                             ItemInFridgeRepository itemInFridgeRepository,
                             ItemInRecipeRepository itemInRecipeRepository,
                             RecipeComponentRepository recipeComponentRepository,
                             RecipeIngredientRepository recipeIngredientRepository,
                             RecipeRefactoredRepository recipeRefactoredRepository,
                             RecipeStepRepository recipeStepRepository,
                             PasswordEncoder passwordEncoder
    ) {
        this.accountRepository = accountRepository;
        this.itemRepository = itemRepository;
        this.recipeRepository = recipeRepository;
        this.fridgeRepository = fridgeRepository;
        this.cookwareRepository = cookwareRepository;
        this.itemInFridgeRepository = itemInFridgeRepository;
        this.itemInRecipeRepository = itemInRecipeRepository;
        this.recipeComponentRepository = recipeComponentRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.recipeRefactoredRepository = recipeRefactoredRepository;
        this.recipeStepRepository = recipeStepRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener
    @Transactional
    public void loadInitialData(ContextRefreshedEvent event) {
        log.info("Loading initial data to database...");

        List<Account> accounts = generateAccounts();
        List<Item> items = generateItems();
        List<Fridge> fridges = generateFridges();
        List<Cookware> cookwares = generateCookware();
        List<Recipe> recipes = generateRecipes();


        log.info("Finished loading initial data.");
        log.info("Linking entities...");

        linkFridgeAndItems(fridges, items);
        linkAccountAndFridge(fridges, accounts);
        linkCookwareAndRecipes(cookwares, recipes);
        linkRecipeAndItems(recipes, items);
        linkAccountAndRecipes(accounts, recipes);

        assingRoles(accounts);

        generateDummyRefactoredRecipes();

        log.info("Finished linking entities");
    }

    private List<Account> generateAccounts() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(generateAccount("Emma", "Johnson", true));
        accounts.add(generateAccount("Ethan", "Taylor", false));
        accounts.add(generateAccount("Olivia", "Davis", true));
        accounts.add(generateAccount("Daniel", "Thomas", false));
        accounts.add(generateAccount("Sophia", "Baker", false));
        accounts.add(generateAccount("Liam", "Jones", true));
        accounts.add(generateAccount("Ava", "Williams", false));
        accounts.add(generateAccount("Noah", "Miller", false));
        accounts.add(generateAccount("Grace", "Anderson", true));
        accounts.add(generateAccount("test", "", false));

        Account adminAccount = new Account();
        adminAccount.setUsername("admin");
        adminAccount.setAccountRoles(Set.of(AccountRole.ROLE_ADMIN));
        adminAccount.setPassword(passwordEncoder.encode("secret"));
        accountRepository.save(adminAccount);

        Account sakuAdmAccount = new Account();
        sakuAdmAccount.setUsername("sakuadm");
        sakuAdmAccount.setAccountRoles(Set.of(AccountRole.ROLE_ADMIN));
        sakuAdmAccount.setPassword(passwordEncoder.encode("1234"));
        accountRepository.save(sakuAdmAccount);

        Account konioAdmAccount = new Account();
        konioAdmAccount.setUsername("konioadm");
        konioAdmAccount.setAccountRoles(Set.of(AccountRole.ROLE_ADMIN));
        konioAdmAccount.setPassword(passwordEncoder.encode("1234"));
        accountRepository.save(konioAdmAccount);
        return accounts;
    }

    private List<Item> generateItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Apple", 1234567890123L, 52.0, 0.26, 0.17, 13.81, ItemType.FRUIT));
        items.add(new Item("Banana", 1234567890124L, 89.0, 1.09, 0.33, 22.84, ItemType.FRUIT));
        items.add(new Item("Chicken Breast", 1234567890125L, 165.0, 31.02, 3.57, 0.0, ItemType.POULTRY));
        items.add(new Item("Broccoli", 1234567890126L, 34.0, 2.82, 0.37, 6.64, ItemType.VEGETABLE));
        items.add(new Item("Almonds", 1234567890127L, 579.0, 21.15, 49.93, 21.55, ItemType.NUTS_AND_SEEDS));
        items.add(new Item("Salmon", 1234567890128L, 208.0, 20.42, 13.42, 0.0, ItemType.FISH));
        items.add(new Item("Rice", 1234567890129L, 130.0, 2.69, 0.28, 28.17, ItemType.GRAIN));
        items.add(new Item("Milk", 1234567890130L, 42.0, 3.37, 0.97, 4.99, ItemType.DAIRY));
        items.add(new Item("Egg", 1234567890131L, 155.0, 12.58, 10.61, 1.12, ItemType.DAIRY));
        items.add(new Item("Beef Steak", 1234567890132L, 250.0, 26.0, 15.0, 0.0, ItemType.BEEF));
        items.add(new Item("Orange", 1234567890133L, 47.0, 0.94, 0.12, 11.75, ItemType.FRUIT));
        items.add(new Item("Carrot", 1234567890134L, 41.0, 0.93, 0.24, 9.58, ItemType.VEGETABLE));
        items.add(new Item("Potato", 1234567890135L, 77.0, 2.02, 0.09, 17.58, ItemType.VEGETABLE));
        items.add(new Item("Butter", 1234567890136L, 717.0, 0.85, 81.11, 0.06, ItemType.DAIRY));
        items.add(new Item("Yogurt", 1234567890137L, 59.0, 3.47, 1.46, 7.04, ItemType.DAIRY));
        items.add(new Item("Cheddar Cheese", 1234567890138L, 403.0, 24.9, 33.14, 1.28, ItemType.DAIRY));
        items.add(new Item("Tomato", 1234567890139L, 18.0, 0.88, 0.2, 3.89, ItemType.VEGETABLE));
        items.add(new Item("Pasta", 1234567890140L, 157.0, 5.8, 0.93, 30.92, ItemType.GRAIN));
        items.add(new Item("Tofu", 1234567890141L, 76.0, 8.08, 4.78, 1.87, ItemType.LEGUME));
        items.add(new Item("Avocado", 1234567890142L, 160.0, 2.0, 14.66, 8.53, ItemType.FRUIT));
        return itemRepository.saveAll(items);
    }

    private List<Fridge> generateFridges() {
        List<Fridge> fridges = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            fridges.add(new Fridge());
        }
        return fridgeRepository.saveAll(fridges);
    }

    private List<Cookware> generateCookware() {
        List<Cookware> cookwares = new ArrayList<>();
        cookwares.add(new Cookware("Patelnia"));
        cookwares.add(new Cookware("Piekarnik"));
        cookwares.add(new Cookware("Mały garnek"));
        cookwares.add(new Cookware("Duży garnek"));
        cookwares.add(new Cookware("Mikser"));
        cookwares.add(new Cookware("Nóż"));
        cookwares.add(new Cookware("Termomix"));
        return cookwareRepository.saveAll(cookwares);
    }

    private List<Recipe> generateRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("Recipe 1", "Test recipe 1 desc"));
        recipes.add(new Recipe("Recipe 2", "Test recipe 2 desc"));
        recipes.add(new Recipe("Recipe 3", "Test recipe 3 desc"));
        recipes.add(new Recipe("Recipe 4", "Test recipe 4 desc"));
        recipes.add(new Recipe("Recipe 5", "Test recipe 5 desc"));
        recipes.add(new Recipe("Recipe 6", "Test recipe 6 desc"));
        recipes.add(new Recipe("Recipe 7", "Test recipe 7 desc"));
        recipes.add(new Recipe("Recipe 8", "Test recipe 8 desc"));
        recipes.add(new Recipe("Recipe 9", "Test recipe 9 desc"));
        recipes.add(new Recipe("Recipe 10", "Test recipe 10 desc"));
        recipes.add(new Recipe("Recipe 11", "Test recipe 11 desc"));
        recipes.add(new Recipe("Recipe 12", "Test recipe 12 desc"));
        recipes.add(new Recipe("Recipe 13", "Test recipe 13 desc"));
        recipes.add(new Recipe("Recipe 14", "Test recipe 14 desc"));
        recipes.add(new Recipe("Recipe 15", "Test recipe 15 desc"));
        recipes.add(new Recipe("Recipe 16", "Test recipe 16 desc"));
        recipes.add(new Recipe("Recipe 17", "Test recipe 17 desc"));
        recipes.add(new Recipe("Recipe 18", "Test recipe 18 desc"));
        recipes.add(new Recipe("Recipe 19", "Test recipe 19 desc"));
        recipes.add(new Recipe("Recipe 20", "Test recipe 20 desc"));
        recipes.add(new Recipe("Recipe 21", "Test recipe 21 desc"));
        recipes.add(new Recipe("Recipe 22", "Test recipe 22 desc"));
        return recipeRepository.saveAll(recipes);
    }

    private void generateDummyRefactoredRecipes() {
        // RECIPE INGREDIENT
        RecipeIngredient item1 = new RecipeIngredient();
        item1.setItem(itemRepository.getReferenceById(1L));
        item1.setIsOptional(false);
        item1.setQuantity(100D);
        item1.setQualities(List.of("test", "test1", "test323"));

        RecipeIngredient item2 = new RecipeIngredient();
        item2.setItem(itemRepository.getReferenceById(3L));
        item2.setIsOptional(false);
        item2.setQuantity(200D);
        item2.setQualities(List.of("test00", "test13444"));

        RecipeIngredient item3 = new RecipeIngredient();
        item3.setItem(itemRepository.getReferenceById(8L));
        item3.setQuantity(645D);

        RecipeIngredient item4 = new RecipeIngredient();
        item4.setItem(itemRepository.getReferenceById(7L));
        item4.setQuantity(45D);

        RecipeIngredient item5 = new RecipeIngredient();
        item5.setItem(itemRepository.getReferenceById(4L));
        item5.setQuantity(64D);

        recipeIngredientRepository.saveAll(List.of(item1, item2, item3, item4, item5));
        recipeIngredientRepository.flush();

        // RECIPE STEP
        RecipeStep r1s1 = new RecipeStep();
        r1s1.setDescription("Recipe 1 step 1");
        r1s1.setIngredients(List.of(item3));

        RecipeStep r1s2 = new RecipeStep();
        r1s2.setDescription("Recipe 1 step 2");
        r1s2.setIngredients(List.of(item1, item2));

        RecipeStep r1s3 = new RecipeStep();
        r1s3.setDescription("Recipe 1 step 3");

        RecipeStep r2s1 = new RecipeStep();
        r2s1.setDescription("Recipe 2 step 1");
        r2s1.setIngredients(List.of(item3, item4));

        RecipeStep r2s2 = new RecipeStep();
        r2s2.setDescription("Recipe 2 step 2");
        r2s2.setIngredients(List.of(item1, item5));

        recipeStepRepository.saveAll(List.of(r1s1, r1s2, r1s3, r2s1, r2s2));
        recipeStepRepository.flush();

        //RECIPE COMPONENT
        RecipeComponent r1p1 = new RecipeComponent();
        r1p1.setName("Main part");
        r1p1.setRecipeIngredients(List.of(item3));

        RecipeComponent r1p2 = new RecipeComponent();
        r1p2.setName("Sauce");
        r1p2.setRecipeIngredients(List.of(item1, item2));

        RecipeComponent r2p1 = new RecipeComponent();
        r2p1.setName("DISSSHHHH");
        r2p1.setRecipeIngredients(List.of(item1, item3, item4, item5));

        recipeComponentRepository.saveAll(List.of(r1p1, r1p2, r2p1));
        recipeComponentRepository.flush();

        // RECIPE REFACTORED
        RecipeRefactored r1 = new RecipeRefactored();
        r1.setDescription("Test recipe 1 description");
        r1.setSimpleSteps(List.of("Simple step1", "Setp2", "Another step", "another one"));
        r1.setDetailedSteps(List.of(r1s1, r1s2, r1s3));
        r1.setTips(List.of("Super pro tipik!!!!"));
        r1.setRecipeComponents(List.of(r1p1, r1p2));
        r1.setIngredients(List.of(item1, item2, item3));

        RecipeRefactored r2 = new RecipeRefactored();
        r2.setDescription("Test recipe 1 description");
        r2.setSimpleSteps(List.of("Simple step1", "Setp2", "Another step", "another one"));
        r2.setDetailedSteps(List.of(r2s1, r2s2));
        r2.setTips(List.of("Super pro tipik!!!!"));
        r2.setRecipeComponents(List.of(r2p1));
        r2.setIngredients(List.of(item1, item3, item4, item5));

        recipeRefactoredRepository.saveAll(List.of(r1, r2));
        recipeRefactoredRepository.flush();

        // LINK ITEMS TO RECIPES
        item1.setRecipes(List.of(r1,r2));
        item2.setRecipes(List.of(r1));
        item3.setRecipes(List.of(r1, r2));
        item4.setRecipes(List.of(r2));
        item5.setRecipes(List.of(r2));
    }

    private Account generateAccount(String firstName, String lastName, Boolean premium) {
        Account account = new Account();
        account.setUsername(firstName);
        account.setPassword(passwordEncoder.encode("password" + firstName));
        account.setMail("%s.%s@test.com".formatted(firstName, lastName));
        account.setPremium(premium);
        return accountRepository.save(account);
    }

    private void assingRoles(List<Account> accounts) {
        for (Account account : accounts) {
            account.addRole(AccountRole.ROLE_USER);
            accountRepository.save(account);
        }
    }

    private void linkFridgeAndItems(List<Fridge> fridges, List<Item> items) {
        for (Fridge fridge : fridges) {
            List<ItemInFridge> addedItems = new ArrayList<>();
            Set<Integer> ids = new HashSet<>();
            for (int i = 0; i < 7; i++) {
                int id = random.nextInt(items.size());
                if (ids.contains(id)) {
                    continue;
                }
                ids.add(id);
                ItemInFridge itemInFridge = new ItemInFridge();
                itemInFridge.setFridgeId(fridge.getId());
                itemInFridge.setAmount(random.nextDouble(10, 300));
                itemInFridge.setItem(items.get(id));
                addedItems.add(itemInFridge);
            }
            itemInFridgeRepository.saveAll(addedItems);
            fridge.setItems(addedItems);
            fridgeRepository.save(fridge);
        }
    }

    private void linkRecipeAndItems(List<Recipe> recipes, List<Item> items) {
        for (Recipe recipe : recipes) {
            List<ItemInRecipe> addedItems = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                ItemInRecipe itemInRecipe = new ItemInRecipe();
                itemInRecipe.setRecipeId(recipe.getId());
                itemInRecipe.setAmount(random.nextDouble(10, 300));
                itemInRecipe.setItem(items.get(random.nextInt(items.size())));
                addedItems.add(itemInRecipe);
            }
            itemInRecipeRepository.saveAll(addedItems);
            recipe.setItems(addedItems);
            recipeRepository.save(recipe);
        }
    }

    private void linkCookwareAndRecipes(List<Cookware> cookwares, List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            List<Cookware> addedCookwares = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                addedCookwares.add(cookwares.get(random.nextInt(cookwares.size())));
            }
            recipe.setCookware(addedCookwares);
            recipeRepository.save(recipe);
        }
    }

    private void linkAccountAndFridge(List<Fridge> fridges, List<Account> accounts) {
        for (int i = 0; i < accounts.size(); i++) {
            accounts.get(i).setFridge(fridges.get(i));
            fridges.get(i).setOwnerId(accounts.get(i).getId());
            fridgeRepository.save(fridges.get(i));
            accountRepository.save(accounts.get(i));
        }
    }

    private void linkAccountAndRecipes(List<Account> accounts, List<Recipe> recipes) {

        accounts.get(0).setAccountRecipes(List.of(recipes.get(0), recipes.get(1), recipes.get(2)));
        accounts.get(2).setAccountRecipes(List.of(recipes.get(3), recipes.get(4)));
        accounts.get(5).setAccountRecipes(List.of(recipes.get(5), recipes.get(6)));
        accounts.get(6).setAccountRecipes(List.of(recipes.get(7)));
        accounts.get(9).setAccountRecipes(List.of(recipes.get(10), recipes.get(11), recipes.get(12), recipes.get(13), recipes.get(14), recipes.get(15)));
        recipeRepository.saveAll(recipes);
    }

}
