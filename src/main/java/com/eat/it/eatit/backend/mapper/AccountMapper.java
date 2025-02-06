package com.eat.it.eatit.backend.mapper;

import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.simple.AccountSimpleDTO;

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
                RecipeMapper.toDTOList(account.getAccountRecipes()),
                RecipeMapper.toDTOList(account.getLikedRecipes()),
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
        account.setAccountRecipes(RecipeMapper.toEntityList(accountDTO.getAccountRecipes()));
        account.setLikedRecipes(RecipeMapper.toEntityList(accountDTO.getLikedRecipes()));
        account.setAccountRoles(accountDTO.getAccountRoles());
        account.setPremium(accountDTO.getPremium());
        return account;
    }

    public static AccountSimpleDTO toSimpleDTO(Account account) {
        return new AccountSimpleDTO(
                account.getId(),
                account.getUsername(),
                account.getMail(),
                account.getPremium(),
                RecipeMapper.toSimpleDTOList(account.getAccountRecipes()),
                RecipeMapper.toSimpleDTOList(account.getLikedRecipes()),
                account.getAccountRoles()
        );
    }
}
