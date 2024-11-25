package com.eat.it.eatit.backend.controller;

import com.eat.it.eatit.backend.IntegrationTest;
import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.RecipeDTO;
import com.eat.it.eatit.backend.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@IntegrationTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountRepository accountRepository;

    private Account testAccount;

    @BeforeEach
    void init() {
        accountRepository.deleteAll();
        testAccount = new Account();
        testAccount.setUsername("Test Account");
        accountRepository.save(testAccount);
    }

    @AfterEach
    void cleanUp() {
        accountRepository.deleteAll();
    }

    @Test
    void shouldGetAccountById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/account/{id}", testAccount.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Test Account"));
    }

    @Test
    void shouldGetAllAccounts() throws Exception {
        Account testAccount2 = new Account();
        testAccount2.setUsername("Test Account 2");
        accountRepository.save(testAccount2);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/account/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("Test Account"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].username").value("Test Account 2"));
    }

    @Test
    void shouldCreateNewAccount() throws Exception {
        AccountDTO newAccount = new AccountDTO();
        newAccount.setUsername("New account username");
        newAccount.setPassword("test password");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newAccount)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("New account username"));
    }

    @Test
    void shouldDeleteAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/account/{id}", testAccount.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Account deletedAccount = accountRepository.findById(testAccount.getId()).orElse(null);
        assertNull(deletedAccount, "The account should have been deleted from the repository");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/account/{id}", testAccount.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldUpdateAccount() throws Exception {
        AccountDTO updatedAcc = new AccountDTO();
        updatedAcc.setMail("updated@mail.com");
        updatedAcc.setUsername("new uSername");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/account/{id}", testAccount.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAcc)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("new uSername"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mail").value("updated@mail.com"));
    }

    @Test
    void shouldAddRecipesToAccount() throws Exception {
        RecipeDTO recipe1 = new RecipeDTO();
        recipe1.setName("Test recipe 1");
        recipe1.setDescription("Recipe 1 for testing purposes");
        RecipeDTO recipe2 = new RecipeDTO();
        recipe2.setName("Test recipe 2");
        recipe2.setDescription("Recipe 2 for testing purposes");

        List<RecipeDTO> recipes = List.of(recipe1, recipe2);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/account/{id}/recipes", testAccount.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipes)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test recipe 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Recipe 1 for testing purposes"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Test recipe 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value("Recipe 2 for testing purposes"));
    }

    @Test
    void shouldReturnBadRequest_whenAccountWithIdDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/account/{id}", Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenDeletingNonExistentAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/account/{id}", Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenUpdatingNonExistentAccount() throws Exception {
        AccountDTO updatedAcc = new AccountDTO();
        updatedAcc.setMail("updated@mail.com");
        updatedAcc.setUsername("new uSername");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/account/{id}", Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAcc)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenAddingRecipesToNonExistendAccount() throws Exception {
        RecipeDTO recipe1 = new RecipeDTO();
        recipe1.setName("Test recipe 1");
        recipe1.setDescription("Recipe 1 for testing purposes");
        RecipeDTO recipe2 = new RecipeDTO();
        recipe2.setName("Test recipe 2");
        recipe2.setDescription("Recipe 2 for testing purposes");

        Set<RecipeDTO> recipes = Set.of(recipe1, recipe2);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/account/{id}/recipes", Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipes)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}