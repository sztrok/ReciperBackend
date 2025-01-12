package com.eat.it.eatit.backend.service.internal;

import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.data.Fridge;
import com.eat.it.eatit.backend.data.Recipe;
import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.RecipeDTO;
import com.eat.it.eatit.backend.mapper.RecipeMapper;
import com.eat.it.eatit.backend.repository.AccountRepository;
import com.eat.it.eatit.backend.service.FridgeService;
import com.eat.it.eatit.backend.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.eat.it.eatit.backend.mapper.AccountMapper.toDTO;
import static com.eat.it.eatit.backend.mapper.AccountMapper.toEntity;
import static com.eat.it.eatit.backend.utils.account.UpdateAccountFields.updateAccountFields;

/**
 * Service class that handles operations related to user accounts.
 */
@Service
public class InternalAccountService {

    AccountRepository accountRepository;
    FridgeService fridgeService;
    RecipeService recipeService;
    PasswordEncoder passwordEncoder;

    @Autowired
    public InternalAccountService(
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

    public AccountDTO getAccountById(Long id) {
        Account account = findAccount(id);
        if (account == null) {
            return null;
        }
        return toDTO(account);
    }

    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountDTO> accountDTOList = new ArrayList<>();
        for (Account account : accounts) {
            accountDTOList.add(toDTO(account));
        }
        return accountDTOList;
    }

    public AccountDTO addNewAccount(AccountDTO accountDTO) {
        String encryptedPassword = passwordEncoder.encode(accountDTO.getPassword());
        accountDTO.setPassword(encryptedPassword);
        Account account = toEntity(accountDTO);
        accountRepository.save(account);
        Fridge fridge = fridgeService.createFridge(account.getId());
        account.setFridge(fridge);
        return toDTO(account);
    }

    @Transactional
    public AccountDTO updateAccountById(Long id, AccountDTO accountDTO) {
        Account account = findAccount(id);
        if (account == null) {
            return null;
        }
        return updateAccountFields(accountDTO, account, accountRepository);
    }

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

    private Account findAccount(Long id) {
        return accountRepository.findById(id).orElse(null);
    }
}
