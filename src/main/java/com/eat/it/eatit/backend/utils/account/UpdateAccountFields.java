package com.eat.it.eatit.backend.utils.account;

import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.mapper.FridgeMapper;
import com.eat.it.eatit.backend.mapper.RecipeMapper;
import com.eat.it.eatit.backend.repository.AccountRepository;

import static com.eat.it.eatit.backend.mapper.AccountMapper.toDTO;
import static com.eat.it.eatit.backend.utils.UtilsKt.updateField;

public class UpdateAccountFields {

    private UpdateAccountFields() {}

    public static AccountDTO updateAccountFields(AccountDTO accountDTO, Account account, AccountRepository accountRepository) {
        updateField(accountDTO.getUsername(), account::setUsername);
        updateField(accountDTO.getMail(), account::setMail);
        updateField(accountDTO.getPassword(), account::setPassword);
        updateField(accountDTO.getFridge(), f -> account.setFridge(FridgeMapper.toEntity(f))); // Uncomment this if fridge is supposed to be changed
        updateField(accountDTO.getAccountRecipes(), r -> account.setAccountRecipes(RecipeMapper.toEntityList(r)));
        updateField(accountDTO.getPremium(), account::setPremium);
        Account saved = accountRepository.save(account);
        return toDTO(saved);
    }
}
