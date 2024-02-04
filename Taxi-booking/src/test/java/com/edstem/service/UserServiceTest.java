package com.edstem.service;

import com.edstem.contract.request.SignupRequest;
import com.edstem.contract.response.SignupResponse;
import com.edstem.model.Users;
import com.edstem.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private UsersRepository usersRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        usersRepository = Mockito.mock(UsersRepository.class);
        modelMapper = new ModelMapper();
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new UserService(passwordEncoder, usersRepository, modelMapper);
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
}
