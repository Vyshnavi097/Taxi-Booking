package com.edstem.service;

import com.edstem.contract.request.TaxiRequest;
import com.edstem.contract.response.TaxiResponse;
import com.edstem.model.Taxi;
import com.edstem.repository.TaxiRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaxiService {
    private final TaxiRepository taxiRepository;
    private final ModelMapper modelMapper;

    public TaxiResponse taxi(TaxiRequest taxiRequest){
        Taxi taxi=Taxi.builder()
                .driverName(taxiRequest.getDriverName())
                .licenseNumber(taxiRequest.getLicenseNumber())
.currentLocation(taxiRequest.getCurrentLocation())
.build();
taxi=taxiRepository.save(taxi);
        return modelMapper.map(taxi,TaxiResponse.class);}
}