package com.eat.it.eatit.backend.account.api;

import com.eat.it.eatit.backend.account.data.Account;
import com.eat.it.eatit.backend.account.data.AccountDTO;
import com.eat.it.eatit.backend.account.data.AccountMapper;
import com.eat.it.eatit.backend.account.data.AccountRepository;
import com.eat.it.eatit.backend.fridge.data.Fridge;
import com.eat.it.eatit.backend.fridge.data.FridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    AccountRepository accountRepository;
    FridgeRepository fridgeRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(AccountRepository accountRepository, FridgeRepository fridgeRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.fridgeRepository = fridgeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<AccountDTO> getAccountById(Long id) {
        Account account = findAccount(id);
        if(account == null) {
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
        if(account == null) {
            return ResponseEntity.notFound().build();
        }
        accountRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public Account findAccount(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

}
