package com.eat.it.eatit.backend.service.user.account;

import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.account.AccountSimpleDTO;
import com.eat.it.eatit.backend.mapper.AccountMapper;
import com.eat.it.eatit.backend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.eat.it.eatit.backend.utils.account.UpdateAccountFields.updateAccountFields;

@Service
public class UserAccountService {

    AccountRepository accountRepository;

    @Autowired
    public UserAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public AccountDTO updateAccountById(Authentication authentication, AccountDTO accountDTO) {
        Account account = getAccountEntityByName(authentication.getName());
        if (account == null) {
            return null;
        }
        return updateAccountFields(accountDTO, account, accountRepository);
    }

    public AccountSimpleDTO getAccountSimple(String username) {
        Account account = accountRepository.findByUsername(username);
        return AccountMapper.toSimpleDTO(account);
    }

    private Account getAccountEntityByName(String username) {
        return accountRepository.findByUsername(username);
    }

}
