package com.eat.it.eatit.backend.loader;

import com.eat.it.eatit.backend.account.data.Account;
import com.eat.it.eatit.backend.account.data.AccountRepository;
import com.eat.it.eatit.backend.cookware.data.Cookware;
import com.eat.it.eatit.backend.cookware.data.CookwareRepository;
import com.eat.it.eatit.backend.fridge.data.Fridge;
import com.eat.it.eatit.backend.fridge.data.FridgeDTO;
import com.eat.it.eatit.backend.fridge.data.FridgeRepository;
import com.eat.it.eatit.backend.item.data.Item;
import com.eat.it.eatit.backend.item.data.ItemRepository;
import com.eat.it.eatit.backend.recipe.data.Recipe;
import com.eat.it.eatit.backend.recipe.data.RecipeRepository;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    public InitialDataLoader(AccountRepository accountRepository, ItemRepository itemRepository, RecipeRepository recipeRepository, FridgeRepository fridgeRepository, CookwareRepository cookwareRepository) {
        this.accountRepository = accountRepository;
        this.itemRepository = itemRepository;
        this.recipeRepository = recipeRepository;
        this.fridgeRepository = fridgeRepository;
        this.cookwareRepository = cookwareRepository;
    }

    @EventListener
    @Transactional
    public void loadInitialData(ContextRefreshedEvent event){
        log.info("Loading initial data to database...");

        List<Account> accounts = generateAccounts();
        List<Item> items = generateItems();
        List<Fridge> fridges = generateFridges();
        List<Cookware> cookwares = generateCookware();

        log.info("Finished loading initial data.");



        log.info("Linking entities...");



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
        accounts.add(generateAccount("Oliver", "Swift", false));
        return accounts;
    }

    private List<Item> generateItems() {
        List<Item> items = new ArrayList<>();

        items.add(new Item("Apple", 1234567890123L, 52, 0.26, 0.17, 13.81, 150.00));
        items.add(new Item("Banana", 1234567890124L, 89, 1.09, 0.33, 22.84, 120.00));
        items.add(new Item("Chicken Breast", 1234567890125L, 165, 31.02, 3.57, 0.00, 250.00));
        items.add(new Item("Broccoli", 1234567890126L, 34, 2.82, 0.37, 6.64, 80.00));
        items.add(new Item("Almonds", 1234567890127L, 579, 21.15, 49.93, 21.55, 50.00));
        items.add(new Item("Salmon", 1234567890128L, 208, 20.42, 13.42, 0.00, 180.00));
        items.add(new Item("Rice", 1234567890129L, 130, 2.69, 0.28, 28.17, 300.00));
        items.add(new Item("Milk", 1234567890130L, 42, 3.37, 0.97, 4.99, 500.00));
        items.add(new Item("Egg", 1234567890131L, 155, 12.58, 10.61, 1.12, 70.00));
        items.add(new Item("Beef Steak", 1234567890132L, 250, 26.00, 15.00, 0.00, 220.00));
        items.add(new Item("Orange", 1234567890133L, 47, 0.94, 0.12, 11.75, 130.00));
        items.add(new Item("Carrot", 1234567890134L, 41, 0.93, 0.24, 9.58, 60.00));
        items.add(new Item("Potato", 1234567890135L, 77, 2.02, 0.09, 17.58, 400.00));
        items.add(new Item("Butter", 1234567890136L, 717, 0.85, 81.11, 0.06, 30.00));
        items.add(new Item("Yogurt", 1234567890137L, 59, 3.47, 1.46, 7.04, 200.00));
        items.add(new Item("Cheddar Cheese", 1234567890138L, 403, 24.90, 33.14, 1.28, 180.00));
        items.add(new Item("Tomato", 1234567890139L, 18, 0.88, 0.20, 3.89, 90.00));
        items.add(new Item("Pasta", 1234567890140L, 157, 5.80, 0.93, 30.92, 250.00));
        items.add(new Item("Tofu", 1234567890141L, 76, 8.08, 4.78, 1.87, 300.00));
        items.add(new Item("Avocado", 1234567890142L, 160, 2.00, 14.66, 8.53, 110.00));
        return itemRepository.saveAll(items);
    }

    private List<Fridge> generateFridges() {
        List<Fridge> fridges = new ArrayList<>();
        for(int i=0; i<10; i++) {
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


    private Account generateAccount(String firstName, String lastName, Boolean premium) {
        Account account = new Account();
        account.setUsername("%s %s".formatted(firstName, lastName));
        account.setPassword("password");
        account.setMail("%s.%s@test.com".formatted(firstName, lastName));
        account.setPremium(premium);
        return accountRepository.save(account);
    }



    private void linkFridgeAndItems() {

    }

    private void linkCookwareAndRecipes() {

    }

    private void linkAccountAndFridge() {

    }

    private void linkRecipeAndItems() {

    }
}
