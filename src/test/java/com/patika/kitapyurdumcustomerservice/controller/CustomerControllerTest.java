package com.patika.kitapyurdumcustomerservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patika.kitapyurdumcustomerservice.dto.request.CustomerSaveRequest;
import com.patika.kitapyurdumcustomerservice.service.CustomerService;
import jakarta.ws.rs.core.MediaType;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    void save() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        String body = mapper.writeValueAsString(Instancio.create(CustomerSaveRequest.class));

        mockMvc.perform(post("/api/v1/customers")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService, times(1)).save(Mockito.any(CustomerSaveRequest.class));
    }

    @Test
    void getAll() throws Exception {

        mockMvc.perform(get("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService, times(1)).getCustomerList();

    }

}