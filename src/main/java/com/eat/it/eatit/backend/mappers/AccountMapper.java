package com.eat.it.eatit.backend.mappers;

import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.dto.AccountDTO;

public class AccountMapper {

    private AccountMapper() {
    }

    //TODO: add null checks
    public static AccountDTO toDTO(Account account) {
        return new AccountDTO(
                account.getUsername(),
                account.getMail(),
                FridgeMapper.toDTO(account.getFridge()),
                RecipeMapper.toDTOSet(account.getRecipes()),
                account.isPremium());
    }

    public static Account toEntity(AccountDTO accountDTO){
        return new Account(
                accountDTO.getUsername(),
                accountDTO.getMail(),
                FridgeMapper.toEntity(accountDTO.getFridge()),
                RecipeMapper.toEntitySet(accountDTO.getRecipes()),
                accountDTO.isPremium());
    }
}
