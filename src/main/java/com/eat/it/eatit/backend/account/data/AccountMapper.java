package com.eat.it.eatit.backend.account.data;

import com.eat.it.eatit.backend.fridge.data.FridgeMapper;
import com.eat.it.eatit.backend.recipe.data.RecipeMapper;

public class AccountMapper {

    private AccountMapper() {
    }

    public static AccountDTO toDTO(Account account) {
        if (account == null) {
            return new AccountDTO();
        }
        return new AccountDTO(
                account.getUsername(),
                account.getMail(),
                account.getPassword(),
                FridgeMapper.toDTO(account.getFridge()),
                RecipeMapper.toDTOSet(account.getRecipes()),
                account.getPremium(),
                account.getAccountRoles());
    }

    public static Account toEntity(AccountDTO accountDTO) {
        if (accountDTO == null) {
            return new Account();
        }
        return new Account(
                accountDTO.getUsername(),
                accountDTO.getMail(),
                accountDTO.getPassword(),
                FridgeMapper.toEntity(accountDTO.getFridge()),
                RecipeMapper.toEntitySet(accountDTO.getRecipes()),
                accountDTO.getAccountRoles(),
                accountDTO.getPremium());
    }
}
