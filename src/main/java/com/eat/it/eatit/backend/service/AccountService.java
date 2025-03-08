package com.eat.it.eatit.backend.service;

import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.data.Fridge;
import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.auth.request.AccountCreationRequest;
import com.eat.it.eatit.backend.dto.account.AccountSimpleDTO;
import com.eat.it.eatit.backend.enums.AccountRole;
import com.eat.it.eatit.backend.enums.ResponseCookieType;
import com.eat.it.eatit.backend.mapper.AccountMapper;
import com.eat.it.eatit.backend.repository.AccountRepository;
import com.eat.it.eatit.backend.repository.FridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static com.eat.it.eatit.backend.mapper.AccountMapper.toDTO;

/**
 * Service class that handles operations related to user accounts.
 */
@Service
public class AccountService {

    AccountRepository accountRepository;
    FridgeRepository fridgeRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(
            AccountRepository accountRepository,
            FridgeRepository fridgeRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.accountRepository = accountRepository;
        this.fridgeRepository = fridgeRepository;
        this.passwordEncoder = passwordEncoder;
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
        Fridge fridge = new Fridge();
        fridgeRepository.save(fridge);
        account.setFridge(fridge);
        return toDTO(account);
    }

    public List<AccountSimpleDTO> getAllAccountsSimple() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(AccountMapper::toSimpleDTO).toList();
    }

    public AccountSimpleDTO getAccountSimple(String username) {
        Account account = accountRepository.findByUsername(username);
        return AccountMapper.toSimpleDTO(account);
    }

    public AccountDTO getAccount(String username) {
        Account account = accountRepository.findByUsername(username);
        return toDTO(account);
    }

    public List<String> getAccountRoles(String username) {
        Account account = getAccountEntityByName(username);
        if (account == null) {
            return Collections.emptyList();
        }
        return account.getAccountRoles().stream().map(Enum::toString).toList();
    }

    public ResponseCookie getResponseCookie(ResponseCookieType type, String token) {
        if (type == ResponseCookieType.ACCESS_TOKEN) {
            return getAccessCookie(token);
        }
        return getRefreshCookie(token);
    }

    private ResponseCookie getAccessCookie(String token) {
        return ResponseCookie.from("accessToken", token)
                .httpOnly(true)
                .secure(false) //TODO:change to true
                .path("/")
                .maxAge(Duration.ofMinutes(15))
                .sameSite("Strict")
                .build();
    }

    private ResponseCookie getRefreshCookie(String token) {
        return ResponseCookie.from("refreshToken", token)
                .httpOnly(true)
                .secure(false) //TODO:change to true
                .path("/")
                .maxAge(Duration.ofDays(7))
                .sameSite("Strict")
                .build();
    }

    private Account getAccountEntityByName(String username) {
        return accountRepository.findByUsername(username);
    }

}
