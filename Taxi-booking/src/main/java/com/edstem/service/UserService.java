package com.edstem.service;

import com.edstem.contract.request.AccountBalanceRequest;
import com.edstem.contract.request.LoginRequest;
import com.edstem.contract.request.SignupRequest;
import com.edstem.contract.response.AccountBalanceResponse;
import com.edstem.contract.response.LoginResponse;
import com.edstem.contract.response.SignupResponse;
import com.edstem.model.Users;
import com.edstem.repository.UsersRepository;
import com.edstem.security.JwtService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

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

    public LoginResponse Login(LoginRequest request) {
        Users user = usersRepository.findByEmail(request.getEmail());
        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new EntityNotFoundException("login");
        }
        String jwtToken = jwtService.generateToken(user);
        return LoginResponse.builder().token(jwtToken).build();
    }

    public AccountBalanceResponse accountBalance(long userId, AccountBalanceRequest accountBalanceRequest){
        Users users=usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("user not found"));
       Users updated=Users.builder()
               .id(users.getId())
               .name(users.getName())
               .email(users.getEmail())
               .password(users.getPassword())
               .accountBalance(users.getAccountBalance()+accountBalanceRequest.getAccountBalance())
               .build();
       updated =usersRepository.save(updated);
       return modelMapper.map(updated,AccountBalanceResponse.class);
    }
}
