package com.eat.it.eatit.backend.mapper.simple;

import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.dto.simple.AccountSimpleDTO;

public class AccountSimpleMapper {

    private AccountSimpleMapper() {
    }

    public static AccountSimpleDTO toSimpleDTO(Account account) {
        return new AccountSimpleDTO(
                account.getId(),
                account.getUsername(),
                account.getMail(),
                account.getPremium(),
                RecipeSimpleMapper.toSimpleDTOList(account.getAccountRecipes()),
                RecipeSimpleMapper.toSimpleDTOList(account.getLikedRecipes()),
                account.getAccountRoles()
        );
    }

}
