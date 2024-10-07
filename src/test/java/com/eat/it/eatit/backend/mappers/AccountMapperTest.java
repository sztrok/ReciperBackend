package com.eat.it.eatit.backend.mappers;

import com.eat.it.eatit.backend.account.data.Account;
import com.eat.it.eatit.backend.account.data.AccountDTO;
import com.eat.it.eatit.backend.account.data.AccountMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountMapperTest {

    private Account account;
    private AccountDTO accountDTO;

    @Test
    void testEntityToDTOConversion() {
        account = new Account();
        account.setMail("test mail");
        account.setUsername("test username");
        account.setPremium(true);

        accountDTO = AccountMapper.toDTO(account);
        assertEquals(account.getMail(),accountDTO.getMail());
        assertEquals(account.getUsername(),accountDTO.getUsername());
        assertEquals(account.getPremium(),accountDTO.getPremium());
    }

    @Test
    void testDTOToEntityConversion() {
        accountDTO = new AccountDTO();
        accountDTO.setMail("test mail");
        accountDTO.setUsername("test username");
        accountDTO.setPremium(true);

        account = AccountMapper.toEntity(accountDTO);
        assertEquals(accountDTO.getMail(),account.getMail());
        assertEquals(accountDTO.getUsername(),account.getUsername());
        assertEquals(accountDTO.getPremium(),account.getPremium());
    }

    @Test
    void testConversionForNullEntityObject() {
        accountDTO = AccountMapper.toDTO(account);
        assertNotNull(accountDTO);
    }

    @Test
    void testConversionForNullDTOObject() {
        account = AccountMapper.toEntity(accountDTO);
        assertNotNull(account);
    }

}