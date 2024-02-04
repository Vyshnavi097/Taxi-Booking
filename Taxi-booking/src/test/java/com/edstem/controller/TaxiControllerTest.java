package com.edstem.controller;

import com.edstem.contract.request.TaxiRequest;
import com.edstem.contract.response.TaxiResponse;
import com.edstem.service.TaxiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TaxiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaxiService taxiService;

    @Test
    void testTaxi() throws Exception {
        TaxiRequest taxiRequest = new TaxiRequest("David", "KL 02 3421", "payyanur");
        TaxiResponse expectedResponse = new TaxiResponse(1L, "David", "KL 02 3421", "payyanur");
        when(taxiService.taxi(any(TaxiRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(
                        post("/taxi")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(taxiRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }
}

