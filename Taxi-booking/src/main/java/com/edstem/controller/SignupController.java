package com.edstem.controller;

import com.edstem.contract.request.SignupRequest;
import com.edstem.contract.response.SignupResponse;
import com.edstem.service.SignupService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SignupController {
    private final SignupService signupService;

    @PostMapping("/signup")
    public SignupResponse signup(@Valid @RequestBody SignupRequest request)
    {
        return signupService.signUp(request);
    }


}
