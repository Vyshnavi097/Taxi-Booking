package com.edstem.controller;

import com.edstem.contract.request.AccountBalanceRequest;
import com.edstem.contract.request.LoginRequest;
import com.edstem.contract.request.SignupRequest;
import com.edstem.contract.response.AccountBalanceResponse;
import com.edstem.contract.response.LoginResponse;
import com.edstem.contract.response.SignupResponse;
import com.edstem.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/v1")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public SignupResponse signup(@Valid @RequestBody SignupRequest request)
    {
        return userService.signUp(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.Login(request));
    }


   @PutMapping("/{userId}")
   public AccountBalanceResponse accountBalance(@PathVariable long userId, @RequestBody AccountBalanceRequest accountBalanceRequest){
    return userService.accountBalance(userId,accountBalanceRequest);
   }
}
