package com.edstem.service;

import com.edstem.contract.request.AccountBalanceRequest;
import com.edstem.contract.request.LoginRequest;
import com.edstem.contract.request.SignupRequest;
import com.edstem.contract.response.AccountBalanceResponse;
import com.edstem.contract.response.SignupResponse;
import com.edstem.model.Users;
import com.edstem.repository.UsersRepository;
import com.edstem.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private UsersRepository usersRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private JwtService jwtService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        usersRepository = Mockito.mock(UsersRepository.class);
        modelMapper = new ModelMapper();
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        jwtService = Mockito.mock(JwtService.class);
        userService = new UserService(passwordEncoder, usersRepository, modelMapper,jwtService);
    }

    @Test
    void testSignup() {
        SignupRequest request = new SignupRequest("Sree", "Sree@123", "Sree@gmail.com");
        Users users = modelMapper.map(request, Users.class);
        SignupResponse expectedResponse = modelMapper.map(users, SignupResponse.class);

        when(usersRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode("Sree@123")).thenReturn("Sree@123");
        when(usersRepository.save(any())).thenReturn(users);

        SignupResponse actualResponse = userService.signUp(request);

        assertEquals(expectedResponse, actualResponse);
    }
    @Test
    void testUserLogin() {
        when(usersRepository.findByEmail(Mockito.<String>any())).thenReturn(new Users());
        when(passwordEncoder.matches(Mockito.<CharSequence>any(), Mockito.<String>any()))
                .thenReturn(false);
        assertThrows(
                EntityNotFoundException.class,
                () -> userService.Login(new LoginRequest(null, null)));
        verify(usersRepository).findByEmail(Mockito.<String>any());
        verify(passwordEncoder).matches(Mockito.<CharSequence>any(), Mockito.<String>any());
    }
    @Test
    public void testUpdateAccountBalance() {
        Users user = new Users(1L, "Sree", "sree@gmail.com", "sree@123", 0.0);
        AccountBalanceRequest request = new AccountBalanceRequest(1000.0);
        Users updatedAmount = new Users(1L, "Sree", "sree@gmail.com", "sree@123", 1000.0);
        AccountBalanceResponse expectedResponse =
                new ModelMapper().map(updatedAmount, AccountBalanceResponse.class);

        when(usersRepository.findById(user.getId())).thenReturn(Optional.empty());
        assertThrows(
                EntityNotFoundException.class, () -> userService.accountBalance(user.getId(), request));

        when(usersRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(usersRepository.save(any(Users.class))).thenReturn(updatedAmount);

        AccountBalanceResponse actualResponse = userService.accountBalance(user.getId(), request);

        assertEquals(expectedResponse, actualResponse);
    }

}
