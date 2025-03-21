package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.account.AccountSimpleDTO;

public class AccountMapper {

    private AccountMapper() {
    }

    public static AccountDTO toDTO(Account account) {
        if (account == null) {
            return new AccountDTO();
        }
        return new AccountDTO(
                account.getId(),
                account.getUsername(),
                account.getMail(),
                account.getPassword(),
                FridgeMapper.toDTO(account.getFridge()),
                account.getPremium(),
                account.getAccountRoles());
    }

    public static Account toEntity(AccountDTO accountDTO) {
        if (accountDTO == null) {
            return new Account();
        }
        Account account = new Account();
        account.setUsername(accountDTO.getUsername());
        account.setMail(accountDTO.getMail());
        account.setPassword(accountDTO.getPassword());
        account.setFridge(FridgeMapper.toEntity(accountDTO.getFridge()));
        account.setAccountRoles(accountDTO.getAccountRoles());
        account.setPremium(accountDTO.getPremium());
        return account;
    }

    public static AccountSimpleDTO toSimpleDTO(Account account) {
        return new AccountSimpleDTO(
                account.getUsername(),
                account.getMail(),
                account.getPremium(),
                account.getAccountRoles()
        );
    }
}
