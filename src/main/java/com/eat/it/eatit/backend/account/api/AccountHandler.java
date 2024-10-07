package com.eat.it.eatit.backend.account.api;

import com.eat.it.eatit.backend.account.data.Account;
import com.eat.it.eatit.backend.account.data.AccountDTO;
import com.eat.it.eatit.backend.account.data.AccountMapper;
import com.eat.it.eatit.backend.account.data.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountHandler {

    AccountRepository accountRepository;

    @Autowired
    public AccountHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDTO getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElse(null);
        return AccountMapper.toDTO(account);
    }

    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountDTO> accountDTOList = new ArrayList<>();
        for (Account account : accounts) {
            accountDTOList.add(AccountMapper.toDTO(account));
        }
        return accountDTOList;
    }

    public ResponseEntity<AccountDTO> addNewAccount(AccountDTO accountDTO) {
        Account account = AccountMapper.toEntity(accountDTO);
        Account savedAccount = accountRepository.save(account);
        return ResponseEntity.ok(AccountMapper.toDTO(savedAccount));
    }

}
