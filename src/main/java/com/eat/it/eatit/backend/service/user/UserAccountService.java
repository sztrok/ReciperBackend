package com.eat.it.eatit.backend.service.user;

import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.eat.it.eatit.backend.mapper.AccountMapper.toDTO;

@Service
public class UserAccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public UserAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDTO getAccountByName(String username) {
        return toDTO(findAccountByUsername(username));
    }

    public AccountDTO getAccountById(Long id) {
        Account account = findAccount(id);
        if (account == null) {
            return null;
        }
        return toDTO(account);
    }

    private Account findAccount(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    private Account findAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }
}
