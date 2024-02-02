package com.edstem.service;

import com.edstem.contract.request.AccountBalanceRequest;
import com.edstem.contract.request.UsersRequest;
import com.edstem.contract.response.AccountBalanceResponse;
import com.edstem.contract.response.UsersResponse;
import com.edstem.model.Users;
import com.edstem.repository.UsersRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private final BookingService bookingService;

    public UsersResponse signUp(UsersRequest request) {
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
        return modelMapper.map(user, UsersResponse.class);
    }

    public AccountBalanceResponse accountBalance(long userId, AccountBalanceRequest accountBalanceRequest){
        Users users=usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("user not found"));
       users=Users.builder()
               .id(users.getId())
               .name(users.getName())
               .email(users.getEmail())
               .password(users.getPassword())
               .accountBalance(users.getAccountBalance())
               .build();
       Users updated=usersRepository.save(users);
       return modelMapper.map(updated,AccountBalanceResponse.class);
    }
}
