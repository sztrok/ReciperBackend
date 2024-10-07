package com.eat.it.eatit.backend.loader;

import com.eat.it.eatit.backend.account.data.Account;
import com.eat.it.eatit.backend.account.data.AccountRepository;
import com.eat.it.eatit.backend.cookware.data.CookwareRepository;
import com.eat.it.eatit.backend.fridge.data.FridgeRepository;
import com.eat.it.eatit.backend.item.data.Item;
import com.eat.it.eatit.backend.item.data.ItemRepository;
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
        items.add(new Item);
        items.add(new Item);
        items.add(new Item);
        items.add(new Item);
        items.add(new Item);
        items.add(new Item);
        items.add(new Item);
        items.add(new Item);
        items.add(new Item);
        items.add(new Item);
        items.add(new Item);
        items.add(new Item);
        items.add(new Item);
        items.add(new Item);
        items.add(new Item);
        items.add(new Item);
        items.add(new Item);
        return items;
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
