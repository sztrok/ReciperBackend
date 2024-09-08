package com.eat.it.eatit.backend.mappers;

import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.dto.AccountDTO;

public class AccountMapper {

    private AccountMapper() {
    }

    public static AccountDTO toDTO(Account account) {
        if(account == null) {
            return new AccountDTO();
        }
        return new AccountDTO(
                account.getUsername(),
                account.getMail(),
                FridgeMapper.toDTO(account.getFridge()),
                RecipeMapper.toDTOSet(account.getRecipes()),
                account.getPremium());
    }

    public static Account toEntity(AccountDTO accountDTO){
        if(accountDTO == null) {
            return new Account();
        }
        return new Account(
                accountDTO.getUsername(),
                accountDTO.getMail(),
                FridgeMapper.toEntity(accountDTO.getFridge()),
                RecipeMapper.toEntitySet(accountDTO.getRecipes()),
                accountDTO.getPremium());
    }
}
