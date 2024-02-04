package com.edstem.service;

import com.edstem.contract.request.TaxiRequest;
import com.edstem.contract.response.TaxiResponse;
import com.edstem.model.Taxi;
import com.edstem.repository.TaxiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TaxiServiceTest {
    private TaxiRepository taxiRepository;
    private ModelMapper modelMapper;
    private TaxiService taxiService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        taxiRepository = Mockito.mock(TaxiRepository.class);
        modelMapper = new ModelMapper();
        taxiService = new TaxiService(taxiRepository, modelMapper);
    }

    @Test
    void testTaxi() {
        TaxiRequest request = new TaxiRequest("David", "KL 02 3421", "payyanur");
        Taxi taxi = modelMapper.map(request, Taxi.class);
        TaxiResponse expectedResponse = modelMapper.map(taxi, TaxiResponse.class);

        when(taxiRepository.save(any())).thenReturn(taxi);

        TaxiResponse actualResponse = taxiService.taxi(request);

        assertEquals(expectedResponse, actualResponse);
    }
}
