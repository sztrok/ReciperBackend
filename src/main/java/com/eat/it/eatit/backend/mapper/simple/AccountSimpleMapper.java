package com.eat.it.eatit.backend.mapper.simple;

import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.simple.AccountSimpleDTO;

public class AccountSimpleMapper {

    private AccountSimpleMapper() {
    }

    public static AccountSimpleDTO toSimple(AccountDTO accountDTO) {
        return new AccountSimpleDTO(
                accountDTO.getId(),
                accountDTO.getUsername(),
                accountDTO.getMail(),
                accountDTO.getFridge().getId(),
                accountDTO.getPremium(),
                accountDTO.getAccountRoles()
        );
    }


}
