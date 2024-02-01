package com.edstem.service;

import com.edstem.contract.request.SignupRequest;
import com.edstem.contract.response.SignupResponse;
import com.edstem.model.Users;
import com.edstem.repository.UsersRepository;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SignupService {
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;

    public SignupResponse signUp(SignupRequest request) {
        if (usersRepository.existsByEmail(request.getEmail())) {
            throw new EntityExistsException("Invalid Signup");
        }
        Users user =
                Users.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .build();
        user = usersRepository.save(user);
        return modelMapper.map(user, SignupResponse.class);
    }
}
