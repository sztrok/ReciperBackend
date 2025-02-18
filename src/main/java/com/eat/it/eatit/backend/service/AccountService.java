package com.eat.it.eatit.backend.service;

import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.data.Fridge;
import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.auth.request.AccountCreationRequest;
import com.eat.it.eatit.backend.dto.refactored.account.AccountSimpleRefactoredDTO;
import com.eat.it.eatit.backend.enums.AccountRole;
import com.eat.it.eatit.backend.mapper.AccountMapper;
import com.eat.it.eatit.backend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.eat.it.eatit.backend.mapper.AccountMapper.toDTO;

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

    public List<AccountSimpleRefactoredDTO> getAllAccountsSimple() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(AccountMapper::toSimpleDTO).toList();
    }

    public AccountSimpleRefactoredDTO getAccountSimple(String username) {
        Account account = accountRepository.findByUsername(username);
        return AccountMapper.toSimpleDTO(account);
    }

    public List<String> getAccountRoles(String username) {
        Account account = getAccountEntityByName(username);
        if (account == null) {
            return Collections.emptyList();
        }
        return account.getAccountRoles().stream().map(Enum::toString).toList();
    }

    private Account getAccountEntityByName(String username) {
        return accountRepository.findByUsername(username);
    }

}
