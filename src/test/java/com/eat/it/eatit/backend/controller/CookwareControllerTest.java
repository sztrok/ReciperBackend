package com.eat.it.eatit.backend.controller;

import com.eat.it.eatit.backend.IntegrationTest;
import com.eat.it.eatit.backend.data.Cookware;
import com.eat.it.eatit.backend.dto.CookwareDTO;
import com.eat.it.eatit.backend.repository.CookwareRepository;
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

@SpringBootTest
@Transactional
@IntegrationTest
@AutoConfigureMockMvc
class CookwareControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CookwareRepository cookwareRepository;

    private Cookware testCookware;

    @BeforeEach
    void init() {
        cookwareRepository.deleteAll();
        testCookware = new Cookware();
        testCookware.setName("Test Pan");
        cookwareRepository.save(testCookware);
    }

    @AfterEach
    void cleanUp() {
        cookwareRepository.deleteAll();
    }

    @Test
    void shouldCreateCookware() throws Exception {
        CookwareDTO newCookware = new CookwareDTO();
        newCookware.setName("New Pan");
        String newCookwareJson = objectMapper.writeValueAsString(newCookware);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cookware/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCookwareJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Pan"));
    }

    @Test
    void shouldGetAllCookware() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cookware/get_all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(testCookware.getName()));
    }

    @Test
    void shouldGetCookwareById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cookware/get/" + testCookware.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testCookware.getName()));
    }

    @Test
    void shouldUpdateCookware() throws Exception {
        testCookware.setName("Updated Pan");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/cookware/update/" + testCookware.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCookware)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Pan"));
    }


    @Test
    void shouldDeleteCookware() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/cookware/delete/" + testCookware.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cookware/get/" + testCookware.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
