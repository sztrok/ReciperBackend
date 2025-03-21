package com.eat.it.eatit.backend.loader;

import com.eat.it.eatit.backend.data.*;
import com.eat.it.eatit.backend.data.recipe.RecipeComponent;
import com.eat.it.eatit.backend.data.recipe.RecipeIngredient;
import com.eat.it.eatit.backend.data.recipe.Recipe;
import com.eat.it.eatit.backend.data.recipe.RecipeStep;
import com.eat.it.eatit.backend.enums.ItemType;
import com.eat.it.eatit.backend.repository.*;
import com.eat.it.eatit.backend.enums.AccountRole;
import com.eat.it.eatit.backend.repository.recipe.RecipeComponentRepository;
import com.eat.it.eatit.backend.repository.recipe.RecipeIngredientRepository;
import com.eat.it.eatit.backend.repository.recipe.RecipeRepository;
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
    private final FridgeRepository fridgeRepository;
    private final CookwareRepository cookwareRepository;
    private final ItemInFridgeRepository itemInFridgeRepository;

    private final RecipeComponentRepository recipeComponentRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeStepRepository recipeStepRepository;
    private final PasswordEncoder passwordEncoder;
    private final Random random = new Random();

    @Autowired
    public InitialDataLoader(AccountRepository accountRepository,
                             ItemRepository itemRepository,
                             FridgeRepository fridgeRepository,
                             CookwareRepository cookwareRepository,
                             ItemInFridgeRepository itemInFridgeRepository,
                             RecipeComponentRepository recipeComponentRepository,
                             RecipeIngredientRepository recipeIngredientRepository,
                             RecipeRepository recipeRepository,
                             RecipeStepRepository recipeStepRepository,
                             PasswordEncoder passwordEncoder
    ) {
        this.accountRepository = accountRepository;
        this.itemRepository = itemRepository;
        this.fridgeRepository = fridgeRepository;
        this.cookwareRepository = cookwareRepository;
        this.itemInFridgeRepository = itemInFridgeRepository;
        this.recipeComponentRepository = recipeComponentRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.recipeRepository = recipeRepository;
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
        generateCookware();

        log.info("Finished loading initial data.");
        log.info("Linking entities...");

        linkFridgeAndItems(fridges, items);
        linkAccountAndFridge(fridges, accounts);

        assignRoles(accounts);

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

    private void generateCookware() {
        List<Cookware> cookwares = new ArrayList<>();
        cookwares.add(new Cookware("Patelnia"));
        cookwares.add(new Cookware("Piekarnik"));
        cookwares.add(new Cookware("Mały garnek"));
        cookwares.add(new Cookware("Duży garnek"));
        cookwares.add(new Cookware("Mikser"));
        cookwares.add(new Cookware("Nóż"));
        cookwares.add(new Cookware("Termomix"));
        cookwareRepository.saveAll(cookwares);
    }

    private void generateDummyRefactoredRecipes() {
        // RECIPE INGREDIENT
        RecipeIngredient item1 = new RecipeIngredient();
        item1.setItem(itemRepository.findById(1L).orElse(new Item()));
        item1.setIsOptional(false);
        item1.setQuantity(100D);
        item1.setQualities(List.of("test", "test1", "test323"));

        RecipeIngredient item2 = new RecipeIngredient();
        item2.setItem(itemRepository.findById(3L).orElse(new Item()));
        item2.setIsOptional(false);
        item2.setQuantity(200D);
        item2.setQualities(List.of("test00", "test13444"));

        RecipeIngredient item3 = new RecipeIngredient();
        item3.setItem(itemRepository.findById(8L).orElse(new Item()));
        item3.setQuantity(645D);

        RecipeIngredient item4 = new RecipeIngredient();
        item4.setItem(itemRepository.findById(7L).orElse(new Item()));
        item4.setQuantity(45D);

        RecipeIngredient item5 = new RecipeIngredient();
        item5.setItem(itemRepository.findById(4L).orElse(new Item()));
        item5.setQuantity(64D);

        recipeIngredientRepository.saveAll(List.of(item1, item2, item3, item4, item5));

        // RECIPE STEP
        RecipeStep r1s1 = new RecipeStep();
        r1s1.setDescription("Recipe 1 step 1");
        r1s1.getIngredients().addAll(List.of(item1, item2, item5, item4));

        RecipeStep r1s2 = new RecipeStep();
        r1s2.setDescription("Recipe 1 step 2");
        r1s2.getIngredients().addAll(List.of(item1, item3, item4));

        RecipeStep r1s3 = new RecipeStep();
        r1s3.setDescription("Recipe 1 step 3");

        RecipeStep r2s1 = new RecipeStep();
        r2s1.setDescription("Recipe 2 step 1");
        r2s1.getIngredients().addAll(List.of(item4, item1, item2));

        RecipeStep r2s2 = new RecipeStep();
        r2s2.setDescription("Recipe 2 step 2");
        r2s2.getIngredients().add(item5);

        recipeStepRepository.saveAll(List.of(r1s1, r1s2, r1s3, r2s1, r2s2));

        //RECIPE COMPONENT
        RecipeComponent r1p1 = new RecipeComponent();
        r1p1.setName("Main part");
        r1p1.getIngredients().addAll(List.of(item3, item2));

        RecipeComponent r1p2 = new RecipeComponent();
        r1p2.setName("Sauce");
        r1p2.getIngredients().addAll(List.of(item1, item2));

        RecipeComponent r2p1 = new RecipeComponent();
        r2p1.setName("DISSSHHHH");
        r2p1.getIngredients().addAll(List.of(item4, item5, item3));

        recipeComponentRepository.saveAll(List.of(r1p1, r1p2, r2p1));

        // RECIPE REFACTORED
        Recipe r1 = new Recipe();
        r1.setName("Test recipe 1 name");
        r1.setDescription("Test recipe 1 description");
        r1.setSimpleSteps(List.of("Simple step1", "Setp2", "Another step", "another one"));
        r1.getDetailedSteps().addAll(List.of(r1s1, r1s2, r1s3));
        r1.setTips(List.of("Super pro tipik!!!!"));
        r1.getRecipeComponents().addAll(List.of(r1p1, r1p2));
        r1.setOwnerAccount(accountRepository.getReferenceById(2L));
        r1.getLikedAccounts().addAll(List.of(accountRepository.getReferenceById(1L), accountRepository.getReferenceById(3L), accountRepository.getReferenceById(4L)));
        r1.getSavedAccounts().addAll(List.of(accountRepository.getReferenceById(2L), accountRepository.getReferenceById(3L), accountRepository.getReferenceById(5L)));
        r1.getIngredients().addAll(List.of(item1, item2, item3));
        
        Recipe r2 = new Recipe();
        r2.setName("Test recipe 2 name");
        r2.setDescription("Test recipe 1 description");
        r2.setSimpleSteps(List.of("Simple step1", "Setp2", "Another step", "another one"));
        r2.getDetailedSteps().addAll(List.of(r2s1, r2s2));
        r2.setTips(List.of("Super pro tipik333!!!!"));
        r2.getRecipeComponents().add(r2p1);
        r2.setOwnerAccount(accountRepository.getReferenceById(6L));
        r2.getLikedAccounts().addAll(List.of(accountRepository.getReferenceById(3L), accountRepository.getReferenceById(2L)));
        r2.getSavedAccounts().addAll(List.of(accountRepository.getReferenceById(6L), accountRepository.getReferenceById(3L), accountRepository.getReferenceById(8L)));
        r2.getIngredients().addAll(List.of(item3, item4, item5));

        recipeRepository.saveAll(List.of(r1, r2));
    }

    private Account generateAccount(String firstName, String lastName, Boolean premium) {
        Account account = new Account();
        account.setUsername(firstName);
        account.setPassword(passwordEncoder.encode("password" + firstName));
        account.setMail("%s.%s@test.com".formatted(firstName, lastName));
        account.setPremium(premium);
        return accountRepository.save(account);
    }

    private void assignRoles(List<Account> accounts) {
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
                itemInFridge.setQuantity(random.nextDouble(10, 300));
                itemInFridge.setItem(items.get(id));
                addedItems.add(itemInFridge);
            }
            itemInFridgeRepository.saveAll(addedItems);
            fridge.setItems(addedItems);
            fridgeRepository.save(fridge);
        }
    }

    private void linkAccountAndFridge(List<Fridge> fridges, List<Account> accounts) {
        for (int i = 0; i < accounts.size(); i++) {
            accounts.get(i).setFridge(fridges.get(i));
            fridgeRepository.save(fridges.get(i));
            accountRepository.save(accounts.get(i));
        }
    }

}
