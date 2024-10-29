package com.eat.it.eatit.backend.account.api;

import com.eat.it.eatit.backend.account.data.*;
import com.eat.it.eatit.backend.fridge.data.Fridge;
import com.eat.it.eatit.backend.fridge.data.FridgeMapper;
import com.eat.it.eatit.backend.fridge.data.FridgeRepository;
import com.eat.it.eatit.backend.recipe.data.Recipe;
import com.eat.it.eatit.backend.recipe.data.RecipeDTO;
import com.eat.it.eatit.backend.recipe.data.RecipeMapper;
import com.eat.it.eatit.backend.recipe.data.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public ResponseEntity<AccountDTO> getAccountById(Long id) {
        Account account = findAccount(id);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(AccountMapper.toDTO(account));
    }

    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountDTO> accountDTOList = new ArrayList<>();
        for (Account account : accounts) {
            accountDTOList.add(AccountMapper.toDTO(account));
        }
        return ResponseEntity.ok(accountDTOList);
    }

    public ResponseEntity<AccountDTO> addNewAccount(AccountDTO accountDTO) {
        String encryptedPassword = passwordEncoder.encode(accountDTO.getPassword());
        accountDTO.setPassword(encryptedPassword);
        Account account = AccountMapper.toEntity(accountDTO);
        Fridge fridge = new Fridge();
        Account savedAccount = accountRepository.save(account);
        fridge.setOwnerId(savedAccount.getId());
        fridgeRepository.save(fridge);
        return ResponseEntity.ok(AccountMapper.toDTO(savedAccount));
    }

    public ResponseEntity<AccountDTO> deleteAccountById(Long id) {
        Account account = findAccount(id);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        accountRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<AccountDTO> updateAccountById(Long id, AccountDTO accountDTO) {
        Account account = findAccount(id);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        Optional.ofNullable(accountDTO.getUsername()).ifPresent(account::setUsername);
        Optional.ofNullable(accountDTO.getMail()).ifPresent(account::setMail);
        Optional.ofNullable(accountDTO.getPassword()).ifPresent(account::setPassword);
        Optional.ofNullable(accountDTO.getFridge()).ifPresent(f -> account.setFridge(FridgeMapper.toEntity(f))); // nie wiem czy to potrzebne, fridge chyba nie powinno dać sie zmieniać
        Optional.ofNullable(accountDTO.getRecipes()).ifPresent(r -> account.setRecipes(RecipeMapper.toEntitySet(r)));
        Optional.ofNullable(accountDTO.getPremium()).ifPresent(account::setPremium);
        Optional.ofNullable(accountDTO.getPassword()).ifPresent(account::setPassword);
        accountRepository.save(account);
        return ResponseEntity.ok(AccountMapper.toDTO(account));
    }

    public ResponseEntity<Set<RecipeDTO>> addRecipesToAccount(Long id, Set<RecipeDTO> recipes) {
        Account account = findAccount(id);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        Set<Recipe> accountRecipes = account.getRecipes();
        accountRecipes.addAll(RecipeMapper.toEntitySet(recipes));
        account.setRecipes(accountRecipes);
        recipeRepository.saveAll(accountRecipes);
        accountRepository.save(account);
        return ResponseEntity.ok(RecipeMapper.toDTOSet(account.getRecipes()));
    }

    private Account findAccount(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

}
