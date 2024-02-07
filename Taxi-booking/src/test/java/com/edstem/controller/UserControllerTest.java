package com.edstem.controller;

import com.edstem.contract.request.AccountBalanceRequest;
import com.edstem.contract.request.LoginRequest;
import com.edstem.contract.request.SignupRequest;
import com.edstem.contract.request.TaxiRequest;
import com.edstem.contract.response.AccountBalanceResponse;
import com.edstem.contract.response.LoginResponse;
import com.edstem.contract.response.SignupResponse;
import com.edstem.contract.response.TaxiResponse;
import com.edstem.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Test
    void testSignUp() throws Exception {
        SignupRequest signupRequest = new SignupRequest();
        SignupResponse signupResponse = new SignupResponse();
        when(userService.signUp(signupRequest)).thenReturn(signupResponse);
    }
    @Test
    public void testSignup_Success() throws Exception {
        SignupRequest signupRequest = SignupRequest.builder()
                .email("testuser@example.com")
                .password("password")
                .name("Test User")
                .build();

        SignupResponse signUpResponse = SignupResponse.builder()
                .name("Test User")
                .build();

        when(userService.signUp(any(SignupRequest.class))).thenReturn(signUpResponse);

        mockMvc.perform(post("/v1/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signupRequest)))
                .andExpect(status().isOk());
    }
    @Test
    void testLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest(null, null);
        LoginResponse expectedResponse =
                new LoginResponse(
                        "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiY2MiLCJpZCI6NCwic3ViIjoiZGV2QGdtYWlsLmNvbSIsImlhdCI6MTcwNjk1OTA3NCwiZXhwIjoxNzA3MDQ1NDc0fQ.oGqlDAn6yXblAUPG-GXL__A5k78nYkfT95tL08y_gxo");
        when(userService.Login(any(LoginRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }
    @Test
    void testAccountBalance() throws Exception {
        AccountBalanceResponse buildResult = AccountBalanceResponse.builder().accountBalance(10.0d).build();
        when(userService.accountBalance(anyLong(), Mockito.<AccountBalanceRequest>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/v1/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new AccountBalanceRequest()));
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"accountBalance\":10.0}"));
    }



}
