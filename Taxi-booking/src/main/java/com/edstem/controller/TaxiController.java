package com.edstem.controller;

import com.edstem.contract.request.TaxiRequest;
import com.edstem.contract.response.TaxiResponse;
import com.edstem.service.TaxiService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class TaxiController {
    private final TaxiService taxiService;
@PostMapping("/taxi")
    public TaxiResponse taxi(@RequestBody TaxiRequest taxiRequest){
        return taxiService.taxi(taxiRequest);
    }
}
