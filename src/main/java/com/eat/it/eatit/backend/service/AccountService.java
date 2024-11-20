package com.eat.it.eatit.backend.service;

import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.data.Fridge;
import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.mapper.FridgeMapper;
import com.eat.it.eatit.backend.repository.AccountRepository;
import com.eat.it.eatit.backend.repository.FridgeRepository;
import com.eat.it.eatit.backend.data.Recipe;
import com.eat.it.eatit.backend.dto.RecipeDTO;
import com.eat.it.eatit.backend.mapper.RecipeMapper;
import com.eat.it.eatit.backend.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.eat.it.eatit.backend.utils.UtilsKt.updateField;
import static com.eat.it.eatit.backend.mapper.AccountMapper.*;

/**
 * Service class that handles operations related to user accounts.
 */
@Service
public class AccountService {

    AccountRepository accountRepository;
    FridgeRepository fridgeRepository;
    RecipeRepository recipeRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(
            AccountRepository accountRepository,
            FridgeRepository fridgeRepository,
            PasswordEncoder passwordEncoder,
            RecipeRepository recipeRepository
    ) {
        this.accountRepository = accountRepository;
        this.fridgeRepository = fridgeRepository;
        this.passwordEncoder = passwordEncoder;
        this.recipeRepository = recipeRepository;
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

    /**
     * Adds a new account to the repository, encrypting the password, mapping the DTO to an entity,
     * and setting up an associated fridge for the new account.
     *
     * @param accountDTO the data transfer object containing account details to be added
     * @return a ResponseEntity containing the AccountDTO of the newly created account
     */
    @Transactional
    public AccountDTO addNewAccount(AccountDTO accountDTO) {
        String encryptedPassword = passwordEncoder.encode(accountDTO.getPassword());
        accountDTO.setPassword(encryptedPassword);
        Account account = toEntity(accountDTO);
        Fridge fridge = new Fridge();
        Account savedAccount = accountRepository.save(account);
        fridge.setOwnerId(savedAccount.getId());
        fridgeRepository.save(fridge);
        return toDTO(savedAccount);
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
        updateField(accountDTO.getUsername(), account::setUsername);
        updateField(accountDTO.getMail(), account::setMail);
        updateField(accountDTO.getPassword(), account::setPassword);
        updateField(accountDTO.getFridge(), f -> account.setFridge(FridgeMapper.toEntity(f))); // Uncomment this if fridge is supposed to be changed
        updateField(accountDTO.getRecipes(), r -> account.setRecipes(RecipeMapper.toEntityList(r)));
        updateField(accountDTO.getPremium(), account::setPremium);
        Account saved = accountRepository.save(account);
        return toDTO(saved);
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
        List<Recipe> accountRecipes = account.getRecipes().stream().sorted().collect(Collectors.toList());
        accountRecipes.addAll(RecipeMapper.toEntityList(recipes));
        account.setRecipes(accountRecipes);
        recipeRepository.saveAll(accountRecipes);
        accountRepository.save(account);
        return RecipeMapper.toDTOList(account.getRecipes());
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

}
