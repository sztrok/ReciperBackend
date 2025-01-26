package com.eat.it.eatit.backend.service;

import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.data.Fridge;
import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.simple.AccountCreationRequest;
import com.eat.it.eatit.backend.enums.AccountRole;
import com.eat.it.eatit.backend.repository.AccountRepository;
import com.eat.it.eatit.backend.data.Recipe;
import com.eat.it.eatit.backend.dto.RecipeDTO;
import com.eat.it.eatit.backend.mapper.RecipeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.eat.it.eatit.backend.mapper.AccountMapper.toDTO;
import static com.eat.it.eatit.backend.mapper.AccountMapper.*;
import static com.eat.it.eatit.backend.utils.account.UpdateAccountFields.updateAccountFields;
import static com.eat.it.eatit.backend.utils.recipe.UpdateRecipeFields.updateRecipeFields;

/**
 * Service class that handles operations related to user accounts.
 */
@Service
public class AccountService {

    AccountRepository accountRepository;
    FridgeService fridgeService;
    RecipeService recipeService;
    PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(
            AccountRepository accountRepository,
            FridgeService fridgeService,
            PasswordEncoder passwordEncoder,
            RecipeService recipeService
    ) {
        this.accountRepository = accountRepository;
        this.fridgeService = fridgeService;
        this.passwordEncoder = passwordEncoder;
        this.recipeService = recipeService;
    }

    @Transactional
    public AccountDTO createAccount(AccountCreationRequest request) {
        // Sprawdzenie, czy użytkownik z podanym e-mailem już istnieje
        if (accountRepository.existsByMail(request.getEmail())) {
            return new AccountDTO();
        }

        // Tworzenie nowego konta
        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setMail(request.getEmail());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setAccountRoles(Collections.singleton(AccountRole.ROLE_USER));
        accountRepository.save(account);
        Fridge fridge = fridgeService.createFridge(account.getId());
        account.setFridge(fridge);
        // Zapis do bazy danych
        return toDTO(account);
    }


    /**
     * Retrieves an account by its ID and returns it as an AccountDTO.
     *
     * @param id the ID of the account to retrieve
     * @return a ResponseEntity containing the AccountDTO if found, or a 404 Not Found response if not found
     */
    public AccountDTO getAccountById(Long id) {
        Account account = findAccount(id);
        if (account == null) {
            return null;
        }
        return toDTO(account);
    }

    /**
     * Retrieves all accounts from the repository, maps them to DTOs, and returns them in the response.
     *
     * @return a ResponseEntity containing a list of AccountDTO objects representing all accounts.
     */
    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountDTO> accountDTOList = new ArrayList<>();
        for (Account account : accounts) {
            accountDTOList.add(toDTO(account));
        }
        return accountDTOList;
    }

    public List<String> getAccountRoles(String username) {
        Account account = getAccountEntityByName(username);
        if (account == null) {
            return Collections.emptyList();
        }
        return account.getAccountRoles().stream().map(Enum::toString).toList();
    }

    public List<RecipeDTO> getAccountRecipes(Long id) {
        Account account = findAccount(id);
        if (account == null) {
            return Collections.emptyList();
        }
        return toDTO(account).getAccountRecipes();
    }

    public List<RecipeDTO> getLikedRecipes(Long id) {
        Account account = findAccount(id);
        if (account == null) {
            return Collections.emptyList();
        }
        return toDTO(account).getLikedRecipes();
    }

    /**
     * Adds a new account to the repository, encrypting the password, mapping the DTO to an entity,
     * and setting up an associated fridge for the new account.
     *
     * @param accountDTO the data transfer object containing account details to be added
     * @return a ResponseEntity containing the AccountDTO of the newly created account
     */
    public AccountDTO addNewAccount(AccountDTO accountDTO) {
        String encryptedPassword = passwordEncoder.encode(accountDTO.getPassword());
        accountDTO.setPassword(encryptedPassword);
        Account account = toEntity(accountDTO);
        accountRepository.save(account);
        Fridge fridge = fridgeService.createFridge(account.getId());
        account.setFridge(fridge);
        return toDTO(account);
    }

    /**
     * Deletes an account by its ID.
     *
     * @param id the ID of the account to be deleted
     * @return a ResponseEntity containing an OK response if the account is found and deleted,
     * or a 404 Not Found response if the account is not found
     */
    @Transactional
    public boolean deleteAccountById(Long id) {
        Account account = findAccount(id);
        if (account == null) {
            return false;
        }
        accountRepository.deleteById(id);
        return true;
    }

    /**
     * Updates the account with the specified ID using the provided AccountDTO.
     *
     * @param id         the ID of the account to update
     * @param accountDTO the data transfer object containing account details to be updated
     * @return a ResponseEntity containing the updated AccountDTO if the update is successful,
     * or a 404 Not Found response if the account is not found
     */
    @Transactional
    public AccountDTO updateAccountById(Long id, AccountDTO accountDTO) {
        Account account = findAccount(id);
        if (account == null) {
            return null;
        }
        return updateAccountFields(accountDTO, account, accountRepository);
    }

    /**
     * Adds a set of recipes to an account identified by its ID.
     *
     * @param id      the ID of the account to which the recipes will be added
     * @param recipes the set of RecipeDTO objects to be added to the account
     * @return a ResponseEntity containing the updated set of RecipeDTO objects associated with the account,
     * or a 404 Not Found response if the account is not found
     */
    @Transactional
    public List<RecipeDTO> addRecipesToAccount(Long id, List<RecipeDTO> recipes) {
        Account account = findAccount(id);
        if (account == null) {
            return null;
        }
        List<Recipe> accountRecipes = account.getAccountRecipes();
        accountRecipes.addAll(RecipeMapper.toEntityList(recipes));
        account.setAccountRecipes(accountRecipes);
        recipeService.addNewRecipes(accountRecipes);
        accountRepository.save(account);
        return RecipeMapper.toDTOList(account.getAccountRecipes());
    }

    @Transactional
    public List<RecipeDTO> addLikedRecipes(Long id, List<RecipeDTO> recipes) {
        Account account = findAccount(id);
        if (account == null) {
            return Collections.emptyList();
        }
        List<Recipe> likedRecipes = account.getLikedRecipes();
        likedRecipes.addAll(RecipeMapper.toEntityList(recipes));
        account.setLikedRecipes(likedRecipes);
        return RecipeMapper.toDTOList(account.getLikedRecipes());
    }

    @Transactional
    public RecipeDTO updateAccountRecipeById(String username, Long recipeId, RecipeDTO recipeDTO) {
        Account account = getAccountEntityByName(username);
        if (account == null) {
            return null;
        }
        Recipe recipe = account.getAccountRecipes().stream()
                .filter(r -> r.getId().equals(recipeId))
                .findFirst()
                .orElse(null);
        return updateRecipeFields(recipeDTO, recipe);

    }

    //TODO: przemyśleć jak powinno wyglądać usuwanie przepisu z bazy przez użytkownika
    @Transactional
    public boolean deleteAccountRecipe(Long id, Long recipeId) {
        Account account = findAccount(id);
        RecipeDTO accountRecipe = recipeService.getRecipeById(recipeId);
        if (account == null || accountRecipe == null) {
            return false;
        }
//      TODO: recipeService.deleteRecipeById(recipeId);
        account.getAccountRecipes()
                .stream()
                .filter(recipe -> recipe.getId().equals(recipeId))
                .findFirst()
                .ifPresent(recipe -> account.getAccountRecipes().remove(recipe));
        return true;
    }

    @Transactional
    public boolean deleteLikedRecipe(Long id, Long recipeId) {
        Account account = findAccount(id);
        if (account == null) {
            return false;
        }
        account.getLikedRecipes()
                .stream()
                .filter(recipe -> recipe.getId().equals(recipeId))
                .findFirst()
                .ifPresent(recipe -> account.getLikedRecipes().remove(recipe));
        return true;
    }

    public Long getAccountIdByName(String username) {
        return getAccountDTOByName(username).getId();
    }

    private AccountDTO getAccountDTOByName(String username) {
        return toDTO(accountRepository.findByUsername(username));
    }

    private Account getAccountEntityByName(String username) {
        return accountRepository.findByUsername(username);
    }


    /**
     * Retrieves an account by its ID.
     *
     * @param id the ID of the account to retrieve
     * @return the Account entity if found, or null if no account is found with the given ID
     */
    private Account findAccount(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    //TODO: wszystkie metody korzystające z repo zrobić jako private - wtedy można w nich validację ustawić lepszą, będzie lepsza kontrola nad tym co jest zapisywane na bazie

}
